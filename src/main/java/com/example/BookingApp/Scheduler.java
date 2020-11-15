package com.example.BookingApp;

import com.example.BookingApp.accommodation.application.RoomService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Scheduler {

    private final RoomService roomService;

    public Scheduler(RoomService roomService) {
        this.roomService = roomService;
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void makeRoomsAvailable (){
        LocalDate today = LocalDate.now();
        roomService.checkRoomAvailability(today);
    }
}
