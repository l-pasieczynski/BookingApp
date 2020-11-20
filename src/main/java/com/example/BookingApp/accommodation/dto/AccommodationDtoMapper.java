package com.example.BookingApp.accommodation.dto;

import com.example.BookingApp.accommodation.model.Accommodation;

import java.util.List;
import java.util.stream.Collectors;

public class AccommodationDtoMapper {

    private AccommodationDtoMapper() {
    }

    public static List<AccommodationDto> mapToAccommodationDtos(List<Accommodation> accommodationList) {
        return accommodationList.stream()
                .map(AccommodationDtoMapper::mapToAccommodationDto)
                .collect(Collectors.toList());
    }

    private static AccommodationDto mapToAccommodationDto(Accommodation accommodation) {
        return AccommodationDto.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .city(accommodation.getCity())
                .phoneNumber(accommodation.getPhoneNumber())
                .build();
    }

}
