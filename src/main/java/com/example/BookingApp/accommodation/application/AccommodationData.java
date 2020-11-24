package com.example.BookingApp.accommodation.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationData {

    private Long id;
    private String name;
    private String city;
    private Integer phoneNumber;
}
