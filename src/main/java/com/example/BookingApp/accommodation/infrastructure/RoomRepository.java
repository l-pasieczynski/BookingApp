package com.example.BookingApp.accommodation.infrastructure;

import com.example.BookingApp.accommodation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
