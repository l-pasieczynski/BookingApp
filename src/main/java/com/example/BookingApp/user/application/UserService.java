package com.example.BookingApp.user.application;

import com.example.BookingApp.EntityNotFoundException;
import com.example.BookingApp.user.infrastructure.UserRepository;
import com.example.BookingApp.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.findByIDNumber(user.getIDNumber()) == null) {
            userRepository.save(user);
            return user;
        }
        return userRepository.findByIDNumber(user.getIDNumber());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class.getSimpleName()));
    }
}
