package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.accommodation.entity.Reservation;
import com.example.BookingApp.accommodation.infrastructure.ReservationRepository;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void makeReservation (User user, Reservation reservation){
        userService.createUser(user);

    }

}
