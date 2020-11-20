package com.example.BookingApp.accommodation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationDto {

    private Long id;
    private String name;
    private String city;
    private Integer phoneNumber;
}
