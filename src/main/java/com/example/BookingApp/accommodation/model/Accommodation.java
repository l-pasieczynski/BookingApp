package com.example.BookingApp.accommodation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
