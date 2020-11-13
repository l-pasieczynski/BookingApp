package com.example.BookingApp.accommodation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    @JoinTable(name = "accommodation_room", joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> room = new ArrayList<>();
    private String city;
    private String street;
    private String postCode;
    private Integer phoneNumber;
    private boolean animals;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = AccommodationAddInfo.class)
    private List<AccommodationAddInfo> accommodationAddInfo;

}
