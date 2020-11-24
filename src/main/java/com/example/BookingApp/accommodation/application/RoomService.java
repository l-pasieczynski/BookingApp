package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.infrastructure.RoomRepository;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.exception.EntityNotFoundException;
import com.example.BookingApp.reservation.application.ReservationService;
import com.example.BookingApp.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private ReservationService reservationService;

    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Room.class.getSimpleName()));
    }

    public List<Room> findAllByAccommodation(Accommodation accommodation) {
        return roomRepository.findAllByAccommodationOrderByPriceAsc(accommodation);
    }

    public List<Room> findAllFreeRoomByAccommodation(Accommodation accommodation) {
        return findAllByAccommodation(accommodation).stream()
                .filter(Room::isAvailable)
                .sorted(Comparator.comparing(Room::getPrice))
                .collect(Collectors.toList());
    }

    public List<Room> findAllFreeRoomByUserSearch(Integer personQuantity, Double minPrice, Double maxPrice, Accommodation accommodation, LocalDate bookIn, LocalDate bookOut) {
        List<Room> listOfAllSearchedRooms = roomRepository.findByUserQuerySearch(minPrice, maxPrice, accommodation, personQuantity);

        List<Room> availableRooms = listOfAllSearchedRooms.stream()
                .filter(room -> reservationService.isAvailableAtDate(bookIn, room))
                .collect(Collectors.toList());

        List<Room> roomList = availableRooms.stream()
                .filter(room -> room.getMaxPerson().equals(personQuantity))
                .collect(Collectors.toList());

        if (roomList.isEmpty()) {
            return availableRooms;
        }
        return roomList;
    }

    public Room addNewRoom(Room roomToSave, Accommodation accommodation) {
        if (findAllByAccommodation(accommodation).stream()
                .anyMatch(room -> room.getId().equals(roomToSave.getId()))) {
            throw new EntityExistsException();
        }
        roomRepository.save(roomToSave);
        return roomToSave;
    }

    public Room updateRoom(Room room) {
        roomRepository.findById(room.getId()).map(updateRoom -> roomRepository.save(room));
        return findById(room.getId());
    }

    public void deleteRoom(Long id) {
        Room roomToDelete = findById(id);
        roomRepository.delete(roomToDelete);
    }

    public void setRoomAvailabilityIfReservationEndsToday(LocalDate today) {

        List<Reservation> allReservationEndsToday = reservationService.findAllReservationEndsToday(today);

        List<Room> allRoomsReservationEndsToday = allReservationEndsToday.stream()
                .map(reservation -> roomRepository.findById(reservation.roomId().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        for (Room room : allRoomsReservationEndsToday) {
            room.setAvailable(true);
        }
    }

    public void setRoomAvailable(Room room) {
        Room roomForAvailability = findById(room.getId());
        roomForAvailability.setAvailable(true);
        roomRepository.save(roomForAvailability);
    }
}
