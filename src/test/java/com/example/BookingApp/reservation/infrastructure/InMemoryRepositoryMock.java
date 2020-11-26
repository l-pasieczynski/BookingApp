package com.example.BookingApp.reservation.infrastructure;

import com.example.BookingApp.reservation.model.Reservation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.*;

public class InMemoryRepositoryMock implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new HashMap<>();

    private InMemoryRepositoryMock() {
    }

    public static InMemoryRepositoryMock newInstance() {
        return new InMemoryRepositoryMock();
    }

    @Override
    public <S extends Reservation> S save(S s) {
        reservations.put(s.getId(), s);
        return s;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }


    @Override
    public Reservation findByReservationNumber(Integer reservationNumber) {
        Reservation reservation = new Reservation();
        Set<Long> keys = reservations.keySet();
        for (Long key : keys) {
            if (reservations.get(key).getReservationNumber().equals(reservationNumber))
                reservation = reservations.get(key);
        }
        return reservation;
    }

    @Override
    public Reservation findLastByRoomId(Long roomId) {
        List<Reservation> reservationList = new ArrayList<>();
        Set<Long> keys = reservations.keySet();
        for (Long key : keys) {
            if (reservations.get(key).getRoomId().equals(roomId)) {
                reservationList.add(reservations.get(key));
            }
        }
        if(reservationList.size() >= 1){
            return reservationList.get(0);
        }
        return null;
    }

    @Override
    public List<Reservation> findAllByBookOutEquals(LocalDate today) {
        List<Reservation> reservationList = new ArrayList<>();
        Set<Long> keys = reservations.keySet();
        for (Long key : keys) {
            if (reservations.get(key).getBookOut().equals(today)) {
                reservationList.add(reservations.get(key));
            }
        }
        return reservationList;
    }

    @Override
    public List<Reservation> findAllByRoomIdOrderByCreatedDesc(Long roomId) {
        return null;
    }

    @Override
    public List<Reservation> findAllByAccommodationIdOrderByCreatedDesc(Long accommodationId) {
        return null;
    }

    @Override
    public List<Reservation> findAllByUserIdOrderByCreatedDesc(Long userId) {
        return null;
    }

    @Override
    public List<Reservation> findAllByAccommodationIdAndActiveOrderByIdDesc(Long id, boolean active) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public List<Reservation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Reservation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Reservation> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public void deleteAll(Iterable<? extends Reservation> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Reservation> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Reservation> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Reservation> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Reservation getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Reservation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Reservation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Reservation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Reservation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Reservation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Reservation> boolean exists(Example<S> example) {
        return false;
    }
}
