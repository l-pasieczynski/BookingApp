package com.example.BookingApp.reservation.model;

import com.example.BookingApp.accommodation.entity.Accommodation;
import com.example.BookingApp.accommodation.entity.Room;
import com.example.BookingApp.user.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer reservationNumber;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToOne
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodation;
    @OneToOne
    @JoinColumn(name="room_id")
    private Room room;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate bookIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate bookOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @PrePersist
    public void create() {
        this.created = LocalDate.now();
    }

    public LocalDate bookOut(){
        return bookOut;
    }

    public Room roomId() {
        return room;
    }
}
