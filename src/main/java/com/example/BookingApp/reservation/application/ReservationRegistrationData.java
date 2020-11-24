package com.example.BookingApp.reservation.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class ReservationRegistrationData {

    @NotBlank
    private Long accommodationId;
    @NotBlank
    private Long roomId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate bookIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate bookOut;

}
