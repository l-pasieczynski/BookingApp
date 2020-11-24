package com.example.BookingApp.accommodation.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationDomainData {

    private Long id;
    private String name;
    private String city;
    private Integer phoneNumber;
}
