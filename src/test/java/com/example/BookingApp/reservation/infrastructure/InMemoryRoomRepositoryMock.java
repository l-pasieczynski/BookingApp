package com.example.BookingApp.reservation.infrastructure;

import com.example.BookingApp.accommodation.infrastructure.RoomRepository;
import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class InMemoryRoomRepositoryMock implements RoomRepository {

    private final Map<Long, Room> rooms = new HashMap<>();

    private InMemoryRoomRepositoryMock() {
    }

    public static InMemoryRoomRepositoryMock newInstance() {
        return new InMemoryRoomRepositoryMock();
    }

    @Override
    public <S extends Room> S save(S s) {
        rooms.put(s.getId(), s);
        return s;
    }

    @Override
    public List<Room> findByUserQuerySearch(Double minPrice, Double maxPrice, Accommodation accommodation, Integer personQuantity) {
        List<Room> roomList = new ArrayList<>();
        Set<Long> keys = rooms.keySet();
        for (Long key : keys) {
            if (rooms.get(key).getMaxPerson().equals(personQuantity)
                    && rooms.get(key).getPrice() > minPrice && rooms.get(key).getPrice() < maxPrice
                    && rooms.get(key).getAccommodationId().equals(accommodation.getId())) {
                roomList.add(rooms.get(key));
            }
        }
        return roomList;
    }

    @Override
    public List<Room> findAllByAccommodationIdOrderByPriceAsc(Long accommodationId) {
        return null;
    }



    @Override
    public List<Room> findAllByAccommodationIdIn(List<Long> ids) {
        return null;
    }

    @Override
    public List<Room> findAll() {
        return null;
    }

    @Override
    public List<Room> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Room> findAllById(Iterable<Long> iterable) {
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
    public void delete(Room room) {

    }

    @Override
    public void deleteAll(Iterable<? extends Room> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Room> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Room> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Room> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Room> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Room getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Room> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Room> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Room> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Room> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Room> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Room> boolean exists(Example<S> example) {
        return false;
    }
}
