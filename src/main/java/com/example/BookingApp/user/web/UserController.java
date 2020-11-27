package com.example.BookingApp.user.web;

import com.example.BookingApp.errors.ErrorResponse;
import com.example.BookingApp.exception.UnauthorizedException;
import com.example.BookingApp.reservation.application.ReservationService;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.application.UserMapper;
import com.example.BookingApp.user.application.UserRegistrationData;
import com.example.BookingApp.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;


    @GetMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserRegistrationData user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorResponse.generateErrorList(bindingResult);
        }
        return ResponseEntity.ok(UserMapper.mapToDomainUser(userService.findByUsername(user.getUsername())));
    }

    @PutMapping("/login/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody UserRegistrationData user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorResponse.generateErrorList(bindingResult);
        }
        userService.updateUser(userService.findByUsername(user.getUsername()));
        return ResponseEntity.ok(userService.updateUser(UserMapper.mapToUserEntity(user)));
    }

    @GetMapping("/user/{userId}/reservation")
    public List<Reservation> getAllUserReservation(@PathVariable Long userId) {
        if (checkUserAuthority()) {
            return reservationService.findAllReservationOfUser(userService.findById(userId));
        }
        throw new UnauthorizedException();
    }

    @GetMapping("/user/{userId}/reservation/{reservationId}")
    public Reservation getAllUserReservation(@PathVariable Long userId, @RequestParam Long reservationNumber, Principal principal) {
        if (checkUserAuthority() && userService.findByUsername(principal.getName()).equals(userService.findById(userId))) {
            return reservationService.findByReservationNumber(reservationNumber);
        }
        throw new UnauthorizedException();
    }

    private boolean checkUserAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"));
    }

}
