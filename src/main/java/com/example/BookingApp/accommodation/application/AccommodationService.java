package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.accommodation.entity.Accommodation;
import com.example.BookingApp.accommodation.infrastructure.AccommodationRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public static Predicate<Accommodation> accommodationName(String name) {
        return accommodation -> accommodation.getName().startsWith(name);
    }

    public static Predicate<Accommodation> accommodationCity(String city) {
        return accommodation -> accommodation.getCity().startsWith(city);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Accommodation.class.getSimpleName()));
    }

    public List<Accommodation> findByNameOrCity(String input) {
        List<Accommodation> findAccommodationByNameOrCity = findAll().stream()
                .filter(accommodationName(input).or(accommodationCity(input)))
                .collect(Collectors.toList());
        return findAccommodationByNameOrCity;
    }

    public void save(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }

    public void delete(Accommodation accommodation) {
        Accommodation accommodationToDelete = findById(accommodation.getId());
        accommodationRepository.delete(accommodationToDelete);
    }
}
