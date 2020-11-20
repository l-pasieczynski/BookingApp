package com.example.BookingApp.reservation.infrastructure;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByReservationNumber(Integer reservationNumber);

    Reservation findLastByRoomId(Long roomId);

    List<Reservation> findAllByBookOutEquals(LocalDate today);

    List<Reservation> findAllByRoomOrderByCreatedDesc(Room room);

    List<Reservation> findAllByAccommodationOrderByCreatedDesc(Accommodation accommodation);

    List<Reservation> findAllByUserOrderByCreatedDesc(User user);
}
