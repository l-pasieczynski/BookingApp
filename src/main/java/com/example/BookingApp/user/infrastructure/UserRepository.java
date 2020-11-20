package com.example.BookingApp.user.infrastructure;

import com.example.BookingApp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByIDNumber(Integer IDNumber);

    User findByUsername(String username);

}
