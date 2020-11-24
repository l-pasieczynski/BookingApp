package com.example.BookingApp.accommodation.dto;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.currency.application.CurrencyService;

import java.time.LocalDate;

public class RoomDtoMapper {

    private RoomDtoMapper() {
    }

    public static RoomDto mapToRoomDto(Accommodation accommodation, Room room, LocalDate availability, Double dollar, Double euro) {
        return RoomDto.builder()
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
