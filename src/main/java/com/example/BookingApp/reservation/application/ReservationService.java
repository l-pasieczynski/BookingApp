package com.example.BookingApp.reservation.application;

import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.exception.EntityNotFoundException;
import com.example.BookingApp.reservation.infrastructure.ReservationRepository;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.application.UserDomainData;
import com.example.BookingApp.user.application.UserRegistrationData;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserService userService;


    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Reservation.class.getSimpleName()));
    }

    public Reservation findByReservationNumber(Long reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber);
    }

    public List<Reservation> findAllReservationOfRoom(Long roomId) {
        return reservationRepository.findAllByRoomIdOrderByCreatedDesc(roomId);
    }

    public List<Reservation> findAllReservationOfAccommodation(Long accommodationId) {
        return reservationRepository.findAllByAccommodationIdOrderByCreatedDesc(accommodationId);
    }

    public List<Reservation> findAllReservationOfUser(User user) {
        return reservationRepository.findAllByUserIdOrderByCreatedDesc(user.getId());
    }

    public Reservation findLastReservationOfRoom(Long roomId) {
        List<Reservation> allRoomReservation = new ArrayList<>();
        allRoomReservation = reservationRepository.findByRoomIdOrderByIdDesc(roomId);
        if (allRoomReservation.isEmpty()){
            return null;
        }
        return allRoomReservation.get(0);
    }

    public LocalDate findWhenRoomAvailable(Long roomId) {
        if (findLastReservationOfRoom(roomId) == null) {
            return LocalDate.now();
        }
        return findLastReservationOfRoom(roomId).getBookOut().plusDays(1);
    }

    public boolean isAvailableAtDate(LocalDate bookIn, Long roomId) {
        if (findWhenRoomAvailable(roomId) == null) {
            return true;
        }
        return bookIn.compareTo(findWhenRoomAvailable(roomId)) >= 0;
    }

    public List<Reservation> findAllReservationEndsToday(LocalDate today) {
        return reservationRepository.findAllByBookOutEquals(today);
    }

    public Reservation makeReservation(UserRegistrationData reservationUser, Long accommodationId, Room room, LocalDate bookIn, LocalDate bookOut) {

        UserDomainData user = checkIsUserAlreadyRegistered(reservationUser);

        if (isAvailableAtDate(bookIn, room.getId())) {
            String reservationNumberConcat = LocalDate.now().toString().replaceAll("-", "") + "00" + user.getId();
            Long reservationNumber = Long.parseLong(reservationNumberConcat);

            Reservation reservation = new Reservation().toBuilder()
                    .accommodationId(accommodationId)
                    .reservationNumber(reservationNumber)
                    .bookIn(bookIn)
                    .bookOut(bookOut)
                    .roomId(room.getId())
                    .userId(user.getId())
                    .active(true)
                    .build();

            reservationRepository.save(reservation);
            return reservation;
        }
        throw new EntityExistsException();
    }

    private UserDomainData checkIsUserAlreadyRegistered(UserRegistrationData reservationUser) {
        UserDomainData user = userService.findByIDNumber(reservationUser.getIDNumber());
        if (user == null) {
            return userService.createUser(reservationUser);
        }
        return user;
    }

    public void cancelReservation(Reservation reservation) {
        reservation.toBuilder().active(false).build();
    }

    public void setRoomAvailabilityIfReservationEndsToday(LocalDate today) {

        List<Reservation> allReservationEndsToday = findAllReservationEndsToday(today);

        for (Reservation reservation : allReservationEndsToday) {
            reservationRepository.delete(reservation);
        }
    }

    public List<Room> findAllFreeRoomByAccommodation(Long accommodationId) {
        return roomService.findAllByAccommodation(accommodationId).stream()
                .filter(room -> findAvailableRoomId(accommodationId).contains(room.getId()))
                .sorted(Comparator.comparing(Room::getPrice))
                .collect(Collectors.toList());
    }

    private List<Long> findAvailableRoomId(Long accommodationId) {
        return reservationRepository.findAllByAccommodationIdOrderByCreatedDesc(accommodationId)
                .stream()
                .filter(reservation -> !reservation.isActive())
                .map(Reservation::getRoomId)
                .collect(Collectors.toList());
    }

    public List<Room> findAllFreeRoomByUserSearch(Integer personQuantity, Double minPrice, Double maxPrice, Accommodation accommodation, LocalDate bookIn, LocalDate bookOut) {
        List<Room> listOfAllSearchedRooms = roomService.findByUserQuerySearch(minPrice, maxPrice, accommodation, personQuantity);

        List<Long> reservedRoomsIds = reservationRepository.findAllByAccommodationIdAndActiveOrderByIdDesc(accommodation.getId(), false).stream()
                .map(Reservation::getRoomId)
                .collect(Collectors.toList());

        if (reservedRoomsIds.isEmpty()) {
            return listOfAllSearchedRooms.stream()
                    .filter(room -> room.getMaxPerson().equals(personQuantity))
                    .collect(Collectors.toList());
        }

        List<Room> freeRooms = listOfAllSearchedRooms.stream()
                .filter(room -> reservedRoomsIds.contains(room.getId()))
                .filter(room -> room.getMaxPerson().equals(personQuantity))
                .collect(Collectors.toList());

        if (freeRooms.isEmpty()) {
            return listOfAllSearchedRooms;
        }
        return freeRooms;

    }


}
