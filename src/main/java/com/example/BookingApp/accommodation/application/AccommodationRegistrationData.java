package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.accommodation.model.AccommodationAddInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AccommodationRegistrationData {

    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String postCode;
    @NotBlank
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{3}")
    private Integer phoneNumber;
    private boolean animals;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = AccommodationAddInfo.class)
    private List<AccommodationAddInfo> accommodationAddInfo;

}
