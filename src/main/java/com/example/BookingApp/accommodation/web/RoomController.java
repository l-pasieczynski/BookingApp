package com.example.BookingApp.accommodation.web;

import com.example.BookingApp.accommodation.application.AccommodationService;
import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.dto.RoomDto;
import com.example.BookingApp.accommodation.dto.RoomDtoMapper;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.errors.ErrorResponse;
import com.example.BookingApp.exception.UnauthorizedException;
import com.example.BookingApp.currency.application.CurrencyService;
import com.example.BookingApp.reservation.application.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class RoomController {

    private final AccommodationService accommodationService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final CurrencyService currencyService;


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
        Double dollarValue = currencyService.midDollarValue();
        Double euroValue = currencyService.midEuroValue();
        return RoomDtoMapper.mapToRoomDto(accommodation, room, roomAvailability, dollarValue, euroValue);
    }

    @GetMapping("/searchRoom")
    public List<Room> getRoomByUserQuerySearch(@RequestParam Integer personQuantity,
                                               @RequestParam Double minPrice,
                                               @RequestParam Double maxPrice,
                                               @RequestParam Accommodation accommodation,
                                               @RequestParam LocalDate bookIn,
                                               @RequestParam @Future LocalDate bookOut) {
        return roomService.findAllFreeRoomByUserSearch(personQuantity, minPrice, maxPrice, accommodation, bookIn, bookOut);
    }

    @PostMapping("/admin/accommodation/{accommodationId}/addRoom")
    public ResponseEntity<Object> addRoom(@RequestBody @Valid Room newRoom, @PathVariable Long accommodationId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorResponse.generateErrorList(bindingResult);
        }
        if (checkAdminAuthority()) {
            Accommodation accommodationOfNewRoom = accommodationService.findById(accommodationId);
            return ResponseEntity.ok(roomService.addNewRoom(newRoom, accommodationOfNewRoom));
        }
        throw new UnauthorizedException();
    }

    @PutMapping("/admin/accommodation/{accommodationId}/room/{roomId}")
    public ResponseEntity<Object>  editRoom(@PathVariable("roomId") Long id, @RequestBody @Valid Room room, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ErrorResponse.generateErrorList(bindingResult);
        }
        if (checkAdminAuthority()) {
            return ResponseEntity.ok(roomService.updateRoom(roomService.findById(id)));
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
