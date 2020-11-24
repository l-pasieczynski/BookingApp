package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.application.RoomData;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;

import java.time.LocalDate;

public class RoomDataMapper {

    private RoomDataMapper() {
    }

    public static RoomData mapToRoomDto(Accommodation accommodation, Room room, LocalDate availability, Double dollar, Double euro) {
        return RoomData.builder()
                .accommodationName(accommodation.getName())
                .roomSize(room.getRoomSize())
                .maxPerson(room.getMaxPerson())
                .price(room.getPrice())
                .priceUSD(room.getPrice()/dollar)
                .priceEuro(room.getPrice()/euro)
                .roomStandard(room.getRoomStandard().toString())
                .description(room.getDescription())
                .available(room.isAvailable())
                .firstAvailableDate(availability)
                .build();
    }
}
