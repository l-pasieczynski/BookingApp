package com.example.BookingApp.user.dto;

import com.example.BookingApp.user.model.User;

public class UserDomainMapper {

    private UserDomainMapper() {
    }

    public static UserDomainModel mapToDomainUser (User user){
        return UserDomainModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .active(user.isActive())
                .vip(user.isVip())
                .build();
    }
}
