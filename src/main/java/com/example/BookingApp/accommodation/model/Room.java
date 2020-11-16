package com.example.BookingApp.accommodation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer roomSize;
    private Integer maxPerson;
    private Double price;
    @Enumerated(EnumType.STRING)
    private RoomStandard roomStandard;
    private String description;
    private boolean available;
    @ManyToOne
    private Accommodation accommodation;


}
