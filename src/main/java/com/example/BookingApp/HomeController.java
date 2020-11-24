package com.example.BookingApp;

import com.example.BookingApp.accommodation.application.AccommodationDomainData;
import com.example.BookingApp.accommodation.application.AccommodationMapper;
import com.example.BookingApp.accommodation.application.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final AccommodationService accommodationService;

    @GetMapping
    public List<AccommodationDomainData> getRandomAccommodation() {
        return AccommodationMapper.mapToAccommodationDtos(accommodationService.getRandomAccommodation());
    }

}
