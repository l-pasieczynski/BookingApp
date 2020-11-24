package com.example.BookingApp.reservation.web;

import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.errors.ErrorResponse;
import com.example.BookingApp.exception.UnauthorizedException;
import com.example.BookingApp.reservation.application.ReservationService;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.dto.UserRegistrationData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;

    @PostMapping("/accommodation/{accommodationId}/room/{roomId}/reservation")
    public ResponseEntity<Object> makeReservation(@Valid @RequestBody UserRegistrationData user, @PathVariable @Min(1) Long roomId, @PathVariable @Min(1) Long accommodationId,
                                                  @RequestParam LocalDate bookIn, @RequestParam @Future LocalDate bookOut, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorResponse.generateErrorList(bindingResult);
        }
        Room roomForReservation = roomService.findById(roomId);
        return ResponseEntity.ok(reservationService.makeReservation(user, accommodationId, roomForReservation, bookIn, bookOut));
    }

    @PutMapping("/user/reservation/{ReservationId}")
    public void cancelReservation(@PathVariable Long ReservationId, Principal principal) {
        if (userService.findByUsername(principal.getName()) != null) {
            Reservation reservationToCancel = reservationService.findById(ReservationId);
            reservationService.cancelReservation(reservationToCancel);
        }
        throw new UnauthorizedException();
    }

    @GetMapping("/user/reservation/history")
    public List<Reservation> allUserReservation(Principal principal) {
        if (isUserRegistered(principal)) {
            return reservationService.findAllReservationOfUser(userService.findByUsername(principal.getName()));
        }
        throw new UnauthorizedException();
    }

    @GetMapping("/user/reservation")
    public Reservation findUserReservationByReservationNumber(@RequestParam Integer reservationNumber) {
        return reservationService.findByReservationNumber(reservationNumber);
    }

    @GetMapping("/admin/accommodation/{accommodationId}/reservation")
    public List<Reservation> getAllReservationOfAccommodation(@PathVariable Long accommodationId) {
        return reservationService.findAllReservationOfAccommodation(accommodationId);
    }

    @GetMapping("admin/accommodation/{accommodationId}/room/{roomId}/reservationHistory")
    public List<Reservation> getAllReservationOfRoom(@PathVariable Long roomId) {
        return reservationService.findAllReservationOfRoom(roomId);
    }

    @GetMapping("admin/accommodation/{accommodationId}/room/{roomId}/reservationLast")
    public Reservation getLastReservationOfRoom(@PathVariable Long roomId) {
        return reservationService.findLastReservationOfRoom(roomId);
    }

    private boolean isUserRegistered(Principal principal) {
        return userService.findByUsername(principal.getName()) != null;
    }
}
