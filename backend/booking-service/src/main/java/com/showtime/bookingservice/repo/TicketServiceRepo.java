package com.showtime.bookingservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.bookingservice.model.BookingSeat;

@Repository
public interface TicketServiceRepo extends JpaRepository<BookingSeat, Integer> {

}
