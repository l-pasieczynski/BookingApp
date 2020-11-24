package com.example.BookingApp.accommodation.infrastructure;

import com.example.BookingApp.accommodation.model.Accommodation;
import com.example.BookingApp.accommodation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByAccommodationIdOrderByPriceAsc(Long accommodationId);

    List<Room> findAllByAccommodationIdAndAvailableOrderByPriceAsc(Long accommodationId, boolean available);

    @Query("SELECT r FROM Room r WHERE " +
            "(:price is null or r.price >= :minPrice) and " +
            "(:price is null or r.price <= :maxPrice) and" +
            "(:accommodation is null or r.accommodationId = :accommodation) and" +
            "(:maxPerson is null or r.maxPerson >= :personQuantity)")
    List<Room> findByUserQuerySearch(Double minPrice, Double maxPrice, Accommodation accommodation, Integer personQuantity);

    List<Room> findAllByAccommodationIdIn(List<Long> ids);
}
