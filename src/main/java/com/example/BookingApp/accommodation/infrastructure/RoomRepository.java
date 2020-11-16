package com.example.BookingApp.accommodation.infrastructure;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByAccommodationOrderByPriceAsc(Accommodation accommodation);

    List<Room> findAllByAccommodationAndAvailableOrderByPriceAsc(Accommodation accommodation, boolean available);

    @Query("SELECT r FROM Room r WHERE " +
            "(:price is null or r.price >= :minPrice) and " +
            "(:price is null or r.price <= :maxPrice) and" +
            "(:accommodation is null or r.accommodation = :accommodation) and" +
            "(:maxPerson is null or r.maxPerson >= :personQuantity)")
    List<Room> findByUserQuerySearch(Double minPrice, Double maxPrice, Accommodation accommodation, Integer personQuantity);
}
