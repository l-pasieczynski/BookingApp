package com.example.BookingApp.accommodation.repository;

import com.example.BookingApp.accommodation.entity.Accommodation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {
}
