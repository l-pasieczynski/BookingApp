package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.infrastructure.RoomRepository;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Room.class.getSimpleName()));
    }

    public List<Room> findAllByAccommodation(Long accommodationId) {
        return roomRepository.findAllByAccommodationIdOrderByPriceAsc(accommodationId);
    }

    public Room addNewRoom(Room roomToSave, Long accommodationId) {
        if (findAllByAccommodation(accommodationId).stream()
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

    public List<Room> findByUserQuerySearch(Double minPrice, Double maxPrice, Accommodation accommodation, Integer personQuantity) {
        return roomRepository.findByUserQuerySearch(minPrice, maxPrice, accommodation, personQuantity);
    }
}
