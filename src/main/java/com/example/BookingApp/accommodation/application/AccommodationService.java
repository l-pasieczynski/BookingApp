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
        return accommodation -> accommodation.getName().contains(name);
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
        return findAll().stream()
                .filter(accommodationName(input).or(accommodationCity(input)))
                .collect(Collectors.toList());
    }

    public Accommodation save(Accommodation accommodation) {
        if (findById(accommodation.getId()) == null) {
            accommodationRepository.save(accommodation);
            return accommodation;
        }
        return null;
    }

    //TODO remove method
//    public Accommodation save(Accommodation accommodationToSave) {
//        if (findAll().stream()
//                .anyMatch(accommodation -> accommodation.getId() == accommodationToSave.getId())) {
//            return null;
//        }
//        accommodationRepository.save(accommodationToSave);
//        return accommodationToSave;
//    }

    public void update (Accommodation accommodation){
        accommodationRepository.save(findById(accommodation.getId()));
    }

    public void delete(Accommodation accommodation) {
        Accommodation accommodationToDelete = findById(accommodation.getId());
        accommodationRepository.delete(accommodationToDelete);
    }
}
