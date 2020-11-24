package com.example.BookingApp;

import com.example.BookingApp.reservation.application.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Scheduler {

    private final ReservationService reservationService;

    public Scheduler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void makeRoomsAvailable() {
        LocalDate today = LocalDate.now();
        reservationService.setRoomAvailabilityIfReservationEndsToday(today);
    }
}
