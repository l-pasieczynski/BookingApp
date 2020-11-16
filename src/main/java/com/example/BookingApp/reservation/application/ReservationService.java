package com.example.BookingApp.reservation.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.accommodation.application.RoomService;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import com.example.BookingApp.reservation.infrastructure.ReservationRepository;
import com.example.BookingApp.reservation.model.Reservation;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Reservation.class.getSimpleName()));
    }

    public Reservation findByReservationNumber(Integer reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber);
    }

    public List<Reservation> findAllReservationOfRoom(Room room) {
        return reservationRepository.findAllByRoomOrderByCreatedDesc(room);
    }

    public List<Reservation> findAllReservationOfAccommodation(Accommodation accommodation){
        return reservationRepository.findAllByAccommodationOrderByCreatedDesc(accommodation);
    }

    public List<Reservation> findAllReservationOfUser (User user){
        return reservationRepository.findAllByUserOrderByCreatedDesc(user);
    }

    public Reservation findLastReservationOfRoom (Room room){
        return reservationRepository.findLastByRoomId(room.getId());
    }

    public LocalDate findWhenRoomAvailable(Room room) {
        LocalDate availabilityOfRoom = reservationRepository.findLastByRoomId(room.getId()).bookOut();
        return availabilityOfRoom.plusDays(1);
    }

    public boolean isAvailableAtDate(LocalDate bookIn, Room room) {
        return bookIn.compareTo(findWhenRoomAvailable(room)) >= 0;
    }

    public List<Reservation> findAllReservationEndsToday(LocalDate today) {
        return reservationRepository.findAllByBookOutEquals(today);
    }

    public Reservation makeReservation(User reservationUser, Room room, LocalDate bookIn, Integer howLong) {

        User user = userService.createUser(reservationUser);

        if (isAvailableAtDate(bookIn, room)) {
            LocalDate bookOut = bookIn.plusDays(howLong);
            String reservationNumberConcat = LocalDate.now().toString().replaceAll("-", "") + "00" +  user.getId() + "12";
            Integer reservationNumber = Integer.parseInt(reservationNumberConcat);

            Reservation reservation = new Reservation().toBuilder()
                    .accommodation(room.getAccommodation())
                    .reservationNumber(reservationNumber)
                    .bookIn(bookIn)
                    .bookOut(bookOut)
                    .room(room)
                    .user(user)
                    .active(true)
                    .build();

            reservationRepository.save(reservation);
            return reservation;
        }
        return null;
    }

    public void cancelReservation(Reservation reservation){
        reservation.toBuilder().active(false).build();
        roomService.setRoomAvailable(reservation.getRoom());
    }
}
