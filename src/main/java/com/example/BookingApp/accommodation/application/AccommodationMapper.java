package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.model.Accommodation;

import java.util.List;
import java.util.stream.Collectors;

public class AccommodationMapper {

    private AccommodationMapper() {
    }

    public static List<AccommodationDomainData> mapToAccommodationDtos(List<Accommodation> accommodationList) {
        return accommodationList.stream()
                .map(AccommodationMapper::mapToAccommodationDto)
                .collect(Collectors.toList());
    }

    private static AccommodationDomainData mapToAccommodationDto(Accommodation accommodation) {
        return AccommodationDomainData.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .city(accommodation.getCity())
                .phoneNumber(accommodation.getPhoneNumber())
                .build();
    }

    public static Accommodation mapToAccommodationEntity(AccommodationRegistrationData accommodationRegistrationData) {
        return Accommodation.builder()
                .name(accommodationRegistrationData.getName())
                .city(accommodationRegistrationData.getCity())
                .street(accommodationRegistrationData.getStreet())
                .postCode(accommodationRegistrationData.getPostCode())
                .phoneNumber(accommodationRegistrationData.getPhoneNumber())
                .accommodationAddInfo(accommodationRegistrationData.getAccommodationAddInfo())
                .animals(accommodationRegistrationData.isAnimals())
                .build();
    }

}
