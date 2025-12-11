package com.showtime.bookingservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.showtime.bookingservice.model.Seat;

import jakarta.persistence.LockModeType;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Integer>{
	List<Seat> findBySeatIdIn(List<Integer> seat_id); 

}
