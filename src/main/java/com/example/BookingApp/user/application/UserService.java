package com.example.BookingApp.user.application;

import com.example.BookingApp.exception.EntityNotFoundException;
import com.example.BookingApp.user.infrastructure.RoleRepository;
import com.example.BookingApp.user.infrastructure.UserRepository;
import com.example.BookingApp.user.model.Role;
import com.example.BookingApp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDomainData createUser(UserRegistrationData userRegistrationData) {
        Role userRole = roleRepository.findByName("USER");
        userRegistrationData.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return UserMapper.mapToDomainUser(userRepository.save(UserMapper.mapToUserEntity(userRegistrationData)));
    }

    public UserDomainData findByIDNumber(Integer IDNumber) {
        return UserMapper.mapToDomainUser(userRepository.findByIDNumber(IDNumber));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class.getSimpleName()));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDomainData updateUser(User user) {
        userRepository.findById(user.getId()).map(updateUser -> userRepository.save(user));
        return UserMapper.mapToDomainUser(findById(user.getId()));
    }

}
