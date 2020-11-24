package com.example.BookingApp.user.dto;

import com.example.BookingApp.user.model.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRegistrationData {

    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    @Pattern(regexp = "[a-z]{2}-[0-9]{4}")
    private Integer IDNumber;
    private String city;
    private Set<Role> roles;
}
