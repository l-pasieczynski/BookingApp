package com.example.BookingApp.user.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.user.dto.UserDomainMapper;
import com.example.BookingApp.user.dto.UserDomainModel;
import com.example.BookingApp.user.dto.UserEntityMapper;
import com.example.BookingApp.user.dto.UserRegistrationData;
import com.example.BookingApp.user.infrastructure.RoleRepository;
import com.example.BookingApp.user.infrastructure.UserRepository;
import com.example.BookingApp.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDomainModel createUser(UserRegistrationData userRegistrationData) {
        Role userRole = roleRepository.findByName("USER");
        userRegistrationData.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return UserDomainMapper.mapToDomainUser(userRepository.save(UserEntityMapper.mapToUserEntity(userRegistrationData)));
    }

    public UserDomainModel findByIDNumber(Integer IDNumber) {
        return UserDomainMapper.mapToDomainUser(userRepository.findByIDNumber(IDNumber));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class.getSimpleName()));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateUser(User user) {
        userRepository.findById(user.getId()).map(updateUser -> userRepository.save(user));
    }

}
