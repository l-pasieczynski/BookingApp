package com.example.BookingApp.accommodation.entity;

import com.example.BookingApp.reservation.model.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
