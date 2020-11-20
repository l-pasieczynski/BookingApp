package com.example.BookingApp.accommodation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RoomDto {

    private String accommodationName;
    private Integer roomSize;
    private Integer maxPerson;
    private Double price;
    private Double priceUSD;
    private Double priceEuro;
    private String roomStandard;
    private String description;
    private boolean available;
    private LocalDate firstAvailableDate;
}
