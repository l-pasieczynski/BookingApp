package com.example.BookingApp.accommodation.application;

import com.example.BookingApp.exception.EntityNotFoundException;
import com.example.BookingApp.accommodation.infrastructure.AccommodationRepository;
import com.example.BookingApp.accommodation.model.Accommodation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccommodationService {

    public static final int PAGE_SIZE = 5;
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

    public List<Accommodation> findAllAccommodation(int page) {
        return accommodationRepository.findAllAccommodation(PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Order.desc("name"))));
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
        throw new EntityExistsException();
    }

    public Accommodation update(Accommodation accommodation) {
        accommodationRepository.save(findById(accommodation.getId()));
        return accommodation;
    }

    public void delete(Long id) {
        accommodationRepository.delete(findById(id));
    }

    public List<Accommodation> getRandomAccommodation() {
        List<Accommodation> randomAccommodationList = new ArrayList<>();
        Random random = new Random();
        int i = 0;
        int size = accommodationRepository.findAll().size();
        while (i < 5) {
            int randomInt = random.nextInt(size);
            randomAccommodationList.add(findById(Long.valueOf(randomInt)));
            i++;
        }
        return randomAccommodationList;
    }
}
