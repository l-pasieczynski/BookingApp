package com.example.BookingApp.user.application;

import com.example.BookingApp.user.model.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserDomainData {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private boolean active;
    private boolean vip;
}
