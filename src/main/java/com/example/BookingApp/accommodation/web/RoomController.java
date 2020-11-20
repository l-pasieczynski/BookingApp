package com.example.BookingApp.accommodation.web;

import com.example.BookingApp.UnauthorizedException;
import com.example.BookingApp.accommodation.application.AccommodationService;
import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.dto.RoomDto;
import com.example.BookingApp.accommodation.dto.RoomDtoMapper;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.reservation.application.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final AccommodationService accommodationService;
    private final RoomService roomService;
    private final ReservationService reservationService;


    @GetMapping("/accommodation/{accommodationId}/room")
    public List<Room> getAllRoomsOfAccommodation(@PathVariable("accommodationId") Long id) {
        Accommodation accommodation = accommodationService.findById(id);
        return roomService.findAllByAccommodation(accommodation);
    }

    @GetMapping("/accommodation/{accommodationId}/available")
    public List<Room> getAllAvailableRoomsOfAccommodation(@PathVariable("accommodationId") Long id) {
        return roomService.findAllFreeRoomByAccommodation(accommodationService.findById(id));
    }

    @GetMapping("/accommodation/{accommodationId}/room/{roomId}")
    public RoomDto getRoom(@PathVariable Long accommodationId,
                           @PathVariable Long roomId) {
        Accommodation accommodation = accommodationService.findById(accommodationId);
        Room room = roomService.findById(roomId);
        LocalDate roomAvailability = reservationService.findWhenRoomAvailable(room);
        return RoomDtoMapper.mapToRoomDto(accommodation, room, roomAvailability);
    }

    @GetMapping("/searchRoom")
    public List<Room> getRoomByUserQuerySearch(@RequestParam Integer personQuantity,
                                               @RequestParam Double minPrice,
                                               @RequestParam Double maxPrice,
                                               @RequestParam Accommodation accommodation,
                                               @RequestParam LocalDate bookIn,
                                               @RequestParam LocalDate bookOut) {
        return roomService.findAllFreeRoomByUserSearch(personQuantity, minPrice, maxPrice, accommodation, bookIn, bookOut);
    }

    @PostMapping("/admin/accommodation/{accommodationId}/addRoom")
    public Room addRoom(@RequestBody Room newRoom, @PathVariable Long accommodationId) {
        if (checkAdminAuthority()) {
            Accommodation accommodationOfNewRoom = accommodationService.findById(accommodationId);
            return roomService.addNewRoom(newRoom, accommodationOfNewRoom);
        }
        throw new UnauthorizedException();
    }

    @PutMapping("/admin/accommodation/{accommodationId}/room/{roomId}")
    public void editRoom(@PathVariable("roomId") Long id) {
        if (checkAdminAuthority()) {
            roomService.updateRoom(roomService.findById(id));
        }
        throw new UnauthorizedException();
    }

    @DeleteMapping("/admin/accommodation/{accommodationId}/room/{roomId}")
    public void deleteRoom(@PathVariable("roomId") Long id) {
        roomService.deleteRoom(id);
    }

    private boolean checkAdminAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }
}
