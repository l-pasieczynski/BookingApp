package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.accommodation.entity.Accommodation;
import com.example.BookingApp.accommodation.entity.Room;
import com.example.BookingApp.accommodation.infrastructure.RoomRepository;
import com.example.BookingApp.reservation.application.ReservationService;
import com.example.BookingApp.reservation.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ReservationService reservationService;

    public RoomService(RoomRepository roomRepository, ReservationService reservationService) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
    }

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Room.class.getSimpleName()));
    }

    public List<Room> findAllByAccommodation(Accommodation accommodation) {
        return roomRepository.findAllByAccommodationOrderByPriceAsc(accommodation);
    }



    public boolean isAvailableAtDate(LocalDate dateToCheck, Room room) {
        return dateToCheck.compareTo(reservationService.findWhenRoomAvailable(room)) >= 0;
    }

    public List<Room> findAllFreeRoomByAccommodation(Accommodation accommodation) {
        return findAllByAccommodation(accommodation).stream()
                .filter(Room::isAvailable)
                .sorted(Comparator.comparing(Room::getPrice))
                .collect(Collectors.toList());
    }

    public List<Room> findAllFreeRoomByUserSearch(Integer personQuantity, Double minPrice, Double maxPrice, Accommodation accommodation, LocalDate bookIn) {
        List<Room> listOfAllSearchedRooms = roomRepository.findByUserQuerySearch(minPrice, maxPrice, accommodation, personQuantity);

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

    public Room addNewRoom(Room room) {
        if (findById(room.getId()) == null){
            roomRepository.save(room);
            return room;
        } return null;
    }

    public void updateRoom(Long id, Room room){
        roomRepository.findById(id).map(room1 -> roomRepository.save(room));
    }

    public void deleteRoom (Long id){
        Room roomToDelete = findById(id);
        roomRepository.delete(roomToDelete);
    }

    public void checkRoomAvailability (LocalDate today){

        List<Reservation> allReservationEndsToday = reservationService.findAllReservationEndsToday(today);

        List<Room> allRoomsReservationEndsToday = allReservationEndsToday.stream()
                .map(reservation -> roomRepository.findById(reservation.roomId().getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        for (Room room: allRoomsReservationEndsToday) {
         room.setAvailable(true);
        }
    }
}
