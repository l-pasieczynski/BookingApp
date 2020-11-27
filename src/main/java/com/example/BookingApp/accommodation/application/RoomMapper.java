package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;

import java.time.LocalDate;

public class RoomMapper {

    private RoomMapper() {
    }

    public static RoomDomainData mapToRoomDomain(Accommodation accommodation, Room room, LocalDate availability, Double dollar, Double euro) {
        return RoomDomainData.builder()
                .accommodationName(accommodation.getName())
                .roomSize(room.getRoomSize())
                .maxPerson(room.getMaxPerson())
                .price(room.getPrice())
                .priceUSD(room.getPrice() / dollar)
                .priceEuro(room.getPrice() / euro)
                .roomStandard(room.getRoomStandard().toString())
                .description(room.getDescription())
                .firstAvailableDate(availability)
                .build();
    }

    public static Room mapToRoomEntity(RoomRegistrationData roomRegistrationData, Long accommodationId) {
        return Room.builder()
                .accommodationId(accommodationId)
                .price(roomRegistrationData.getPrice())
                .maxPerson(roomRegistrationData.getMaxPerson())
                .roomSize(roomRegistrationData.getRoomSize())
                .roomStandard(roomRegistrationData.getRoomStandard())
                .description(roomRegistrationData.getDescription())
                .build();
    }

}
