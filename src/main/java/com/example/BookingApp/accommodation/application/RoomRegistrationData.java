package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.model.RoomStandard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class RoomRegistrationData {

    @NotBlank
    @Min(10)
    private Integer roomSize;
    @Min(1)
    private Integer maxPerson;
    private Double price;
    @Enumerated(EnumType.STRING)
    private RoomStandard roomStandard;
    private String description;

}
