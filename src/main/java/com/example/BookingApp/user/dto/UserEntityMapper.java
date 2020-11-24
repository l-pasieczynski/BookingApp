package com.example.BookingApp.user.dto;

import com.example.BookingApp.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserEntityMapper {


    private UserEntityMapper() {
    }

    public static User mapToUserEntity(UserRegistrationData userRegistrationData){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return User.builder()
                .username(userRegistrationData.getUsername())
                .email(userRegistrationData.getEmail())
                .password(passwordEncoder.encode(userRegistrationData.getPassword()))
                .IDNumber(userRegistrationData.getIDNumber())
                .active(true)
                .city(userRegistrationData.getCity())
                .roles(userRegistrationData.getRoles())
                .vip(false)
                .build();
    }
}
