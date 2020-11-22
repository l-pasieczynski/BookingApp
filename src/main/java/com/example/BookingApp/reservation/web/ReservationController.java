package com.example.BookingApp.reservation.web;

import com.example.BookingApp.UnauthorizedException;
import com.example.BookingApp.accommodation.application.AccommodationService;
import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.reservation.application.ReservationService;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.dto.UserRegistrationData;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private final AccommodationService accommodationService;

    @PostMapping("/accommodation/{accommodationId}/room/{roomId}/reservation")
    public Reservation makeReservation(@RequestBody UserRegistrationData user, @PathVariable Long roomId,
                                       @RequestParam LocalDate bookIn, @RequestParam LocalDate bookOut) {

        Room roomForReservation = roomService.findById(roomId);
        return reservationService.makeReservation(user, roomForReservation, bookIn, bookOut);
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
        return reservationService.findAllReservationOfAccommodation(accommodationService.findById(accommodationId));
    }

    @GetMapping("admin/accommodation/{accommodationId}/room/{roomId}/reservationHistory")
    public List<Reservation> getAllReservationOfRoom(@PathVariable Long roomId) {
        return reservationService.findAllReservationOfRoom(roomService.findById(roomId));
    }

    @GetMapping("admin/accommodation/{accommodationId}/room/{roomId}/reservationLast")
    public Reservation getLastReservationOfRoom(@PathVariable Long roomId) {
        return reservationService.findLastReservationOfRoom(roomService.findById(roomId));
    }

    private boolean isUserRegistered(Principal principal) {
        return userService.findByUsername(principal.getName()) != null;
    }
}
