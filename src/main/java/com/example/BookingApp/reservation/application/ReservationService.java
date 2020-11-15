package com.example.BookingApp.reservation.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.accommodation.entity.Room;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.reservation.infrastructure.ReservationRepository;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Reservation.class.getSimpleName()));
    }

    public Reservation findByReservationNumber (Integer reservationNumber){
        return reservationRepository.findByReservationNumber(reservationNumber);
    }

    public LocalDate findWhenRoomAvailable(Room room) {
        LocalDate availabilityOfRoom = reservationRepository.findFirstByRoomId(room.getId()).bookOut();
        return availabilityOfRoom.plusDays(1);
    }

    public List<Reservation> findAllReservationEndsToday (LocalDate today){
        return reservationRepository.findAllByBookOutEquals(today);
    }

    public void makeReservation (User user, Reservation reservation){
        userService.createUser(user);

    }

}
