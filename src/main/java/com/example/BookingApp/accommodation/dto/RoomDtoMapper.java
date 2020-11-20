package com.example.BookingApp.accommodation.dto;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;

import java.time.LocalDate;

public class RoomDtoMapper {

    private RoomDtoMapper() {
    }

    public static RoomDto mapToRoomDto(Accommodation accommodation, Room room, LocalDate availability) {
        return RoomDto.builder()
                .accommodationName(accommodation.getName())
                .roomSize(room.getRoomSize())
                .maxPerson(room.getMaxPerson())
                .price(room.getPrice())
                .roomStandard(room.getRoomStandard().toString())
                .description(room.getDescription())
                .available(room.isAvailable())
                .firstAvailableDate(availability)
                .build();
    }
}
