package com.example.BookingApp.reservation.model;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.reservation.validator.ReservationNumber;
import com.example.BookingApp.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ReservationNumber
    private Integer reservationNumber;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long accommodationId;
    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate bookIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate bookOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;
    private boolean active;

    @PrePersist
    public void create() {
        this.created = LocalDate.now();
    }

    public LocalDate bookIn() {
        return bookIn;
    }

    public LocalDate bookOut() {
        return bookOut;
    }

    public Room roomId() {
        return room;
    }
}
