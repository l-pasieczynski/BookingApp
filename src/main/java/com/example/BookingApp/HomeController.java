package com.example.BookingApp;

import com.example.BookingApp.accommodation.application.AccommodationService;
import com.example.BookingApp.accommodation.dto.AccommodationDto;
import com.example.BookingApp.accommodation.dto.AccommodationDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final AccommodationService accommodationService;

    @GetMapping
    public List<AccommodationDto> getRandomAccommodation() {
        return AccommodationDtoMapper.mapToAccommodationDtos(accommodationService.getRandomAccommodation());
    }

}
