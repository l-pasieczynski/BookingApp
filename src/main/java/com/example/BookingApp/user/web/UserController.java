package com.example.BookingApp.user.web;

import com.example.BookingApp.errors.ErrorResponse;
import com.example.BookingApp.user.application.UserService;
import com.example.BookingApp.user.dto.UserDomainMapper;
import com.example.BookingApp.user.dto.UserEntityMapper;
import com.example.BookingApp.user.dto.UserRegistrationData;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserRegistrationData user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorResponse.generateErrorList(bindingResult);
        }
        return ResponseEntity.ok(UserDomainMapper.mapToDomainUser(userService.findByUsername(user.getUsername())));
    }

    @PutMapping("/login/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody UserRegistrationData user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ErrorResponse.generateErrorList(bindingResult);
        }
        userService.updateUser(userService.findByUsername(user.getUsername()));
        return ResponseEntity.ok(userService.updateUser(UserEntityMapper.mapToUserEntity(user)));
    }

}
