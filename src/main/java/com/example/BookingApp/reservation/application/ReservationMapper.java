package com.example.BookingApp.reservation.application;

import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.model.User;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static ReservationDomainData mapToDomainReservation(Reservation reservation, User user, Room room) {
        return ReservationDomainData.builder()
                .reservationNumber(reservation.getReservationNumber())
                .accommodationId(reservation.getAccommodationId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roomId(room.getId())
                .roomSize(room.getRoomSize())
                .maxPerson(room.getMaxPerson())
                .price(reservation.getPrice())
                .bookIn(reservation.getBookIn())
                .bookOut(reservation.getBookOut())
                .active(reservation.isActive())
                .build();
    }

    public static Reservation mapToReservationEntity(ReservationRegistrationData reservationRegistrationData, User user, Integer reservationNumber, Double price) {
        return Reservation.builder()
                .accommodationId(reservationRegistrationData.getAccommodationId())
                .roomId(reservationRegistrationData.getRoomId())
                .userId(user.getId())
                .reservationNumber(reservationNumber)
                .bookIn(reservationRegistrationData.getBookIn())
                .bookOut(reservationRegistrationData.getBookOut())
                .active(true)
                .price(price)
                .build();
    }

}
