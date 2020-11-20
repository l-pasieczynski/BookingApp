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
        User userByIDNum = findByIDNumber(user.getIDNumber());
        if (userByIDNum == null) {
            userRepository.save(user);
            return user;
        }
        return userByIDNum;
    }

    public User findByIDNumber(Integer IDNumber) {
        return userRepository.findByIDNumber(IDNumber);
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
