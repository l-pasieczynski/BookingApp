package com.example.BookingApp.accommodation.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Min(10)
    private Integer roomSize;
    @Min(1)
    private Integer maxPerson;
    private Double price;
    @Enumerated(EnumType.STRING)
    private RoomStandard roomStandard;
    private String description;
    private boolean available;
    @ManyToOne
    private Accommodation accommodation;


}
