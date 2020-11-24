package com.example.BookingApp.user.application;

import com.example.BookingApp.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDomainData mapToDomainUser(User user) {
        return UserDomainData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .active(user.isActive())
                .vip(user.isVip())
                .build();
    }

    public static User mapToUserEntity(UserRegistrationData userRegistrationData) {

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
