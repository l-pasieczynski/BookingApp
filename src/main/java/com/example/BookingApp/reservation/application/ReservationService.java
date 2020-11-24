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
import java.time.LocalDate;
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

    public Reservation findByReservationNumber(Integer reservationNumber) {
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
        return reservationRepository.findLastByRoomId(roomId);
    }

    public LocalDate findWhenRoomAvailable(Room room) {
        LocalDate availabilityOfRoom = reservationRepository.findLastByRoomId(room.getId()).bookOut();
        return availabilityOfRoom.plusDays(1);
    }

    public boolean isAvailableAtDate(LocalDate bookIn, Room room) {
        return bookIn.compareTo(findWhenRoomAvailable(room)) >= 0;
    }

    public List<Reservation> findAllReservationEndsToday(LocalDate today) {
        return reservationRepository.findAllByBookOutEquals(today);
    }

    public Reservation makeReservation(UserRegistrationData reservationUser, Long accommodationId, Room room, LocalDate bookIn, LocalDate bookOut) {

        UserDomainData user = checkIsUserAlreadyRegistered(reservationUser);

        if (isAvailableAtDate(bookIn, room)) {
            String reservationNumberConcat = LocalDate.now().toString().replaceAll("-", "") + "00" + user.getId() + "12";
            Integer reservationNumber = Integer.parseInt(reservationNumberConcat);

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

        List<Room> availableRooms = listOfAllSearchedRooms.stream()
                .filter(room -> isAvailableAtDate(bookIn, room))
                .collect(Collectors.toList());

        List<Room> roomList = availableRooms.stream()
                .filter(room -> room.getMaxPerson().equals(personQuantity))
                .collect(Collectors.toList());

        if (roomList.isEmpty()) {
            return availableRooms;
        }
        return roomList;
    }


}
