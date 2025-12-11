package com.showtime.bookingservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.bookingservice.model.Booking;
import com.showtime.bookingservice.model.User;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer>{
	  List<Booking> findByUser(User user);
}
