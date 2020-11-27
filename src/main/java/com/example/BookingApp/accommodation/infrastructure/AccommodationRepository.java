package com.example.BookingApp.accommodation.infrastructure;

import com.example.BookingApp.accommodation.model.Accommodation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("SELECT a from Accommodation a ORDER BY a.name DESC ")
    List<Accommodation> findAllAccommodation(Pageable page);
}
