package com.example.BookingApp.accommodation.web;

import com.example.BookingApp.UnauthorizedException;
import com.example.BookingApp.accommodation.application.AccommodationService;
import com.example.BookingApp.accommodation.dto.AccommodationDto;
import com.example.BookingApp.accommodation.dto.AccommodationDtoMapper;
import com.example.BookingApp.accommodation.model.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/accommodation")
    public List<AccommodationDto> getAllAccommodation(@RequestParam(required = false) int page) {
        int pageNumber = page > 0 ? page : 1;
        return AccommodationDtoMapper.mapToAccommodationDtos(accommodationService.findAllAccommodation(pageNumber - 1));
    }

    @GetMapping("/accommodation/{accommodationId}")
    public Accommodation getAccommodationById(@PathVariable("accommodationId") Long id) {
        return accommodationService.findById(id);
    }

    @GetMapping("/accommodation/search")
    public List<Accommodation> getAccommodationByNameOrPlace(@RequestParam String search) {
        return accommodationService.findByNameOrCity(search);
    }

    @PostMapping("/admin/addAccommodation")
    public Accommodation addAccommodation(@RequestBody Accommodation accommodation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return accommodationService.save(accommodation);
        }
        throw new UnauthorizedException();
    }

    @PutMapping("/admin/accommodation/{accommodationId}")
    public Accommodation editAccommodation(@PathVariable("accommodationId") Long id, Principal principal) {
        UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        if (user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return accommodationService.update(accommodationService.findById(id));
        }
        throw new UnauthorizedException();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/accommodation/{accommodationId}")
    public void deleteAccommodation(@PathVariable("accommodationId") Long id) {
        accommodationService.delete(id);
    }

}
