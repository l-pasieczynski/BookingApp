package com.example.BookingApp.reservation.application;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationDomainData {

    private Long reservationNumber;
    private String username;
    private String email;
    private Long accommodationId;
    private Long roomId;
    private Integer roomSize;
    private Integer maxPerson;
    private Double price;
    private LocalDate bookIn;
    private LocalDate bookOut;
    private boolean active;
}
