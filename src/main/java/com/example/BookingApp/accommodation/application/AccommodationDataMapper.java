package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.model.Accommodation;

import java.util.List;
import java.util.stream.Collectors;

public class AccommodationDataMapper {

    private AccommodationDataMapper() {
    }

    public static List<AccommodationData> mapToAccommodationDtos(List<Accommodation> accommodationList) {
        return accommodationList.stream()
                .map(AccommodationDataMapper::mapToAccommodationDto)
                .collect(Collectors.toList());
    }

    private static AccommodationData mapToAccommodationDto(Accommodation accommodation) {
        return AccommodationData.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .city(accommodation.getCity())
                .phoneNumber(accommodation.getPhoneNumber())
                .build();
    }

}
