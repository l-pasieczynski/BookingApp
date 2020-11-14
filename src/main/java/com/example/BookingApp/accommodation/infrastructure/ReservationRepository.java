package com.example.BookingApp.accommodation.infrastructure;

import com.example.BookingApp.accommodation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByReservationNumber(Integer reservationNumber);
}
