package com.example.BookingApp.accommodation.web;

import com.example.BookingApp.accommodation.application.AccommodationService;
import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final RoomService roomService;

    @GetMapping("/accommodation")
    public List<Accommodation> getAllAccommodation() {
        return accommodationService.findAll();
    }

    @GetMapping("/accommodation/search")
    public List<Accommodation> getAccommodationByNameOrPlace(@RequestParam String search) {
        return accommodationService.findByNameOrCity(search);
    }

    @PostMapping("/admin/accommodation")
    public Accommodation addAccommodation(@RequestBody Accommodation accommodation) {
        return accommodationService.save(accommodation);
    }

    @PutMapping("/admin/accommodation/{id}")
    public Accommodation editAccommodation(@PathVariable Long id) {
        return accommodationService.update(accommodationService.findById(id));
    }

    @DeleteMapping("/admin/accommodation/{id}")
    public void deleteAccommodation(@PathVariable Long id) {
        accommodationService.delete(accommodationService.findById(id));
    }

    @GetMapping("/accommodation/{id}")
    public Accommodation getAccommodationById(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @GetMapping("/accommodation/{id}/room")
    public List<Room> getAllRoomsOfAccommodation(@PathVariable Long id) {
        Accommodation accommodation = accommodationService.findById(id);
        return roomService.findAllByAccommodation(accommodation);
    }

    @GetMapping("/accommodation/{id}/available")
    public List<Room> getAllAvailableRoomsOfAccommodation(@PathVariable Long id) {
        return roomService.findAllFreeRoomByAccommodation(accommodationService.findById(id));
    }

}
