package com.showtime.bookingservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.bookingservice.model.Show;

public interface ShowRepo extends JpaRepository<Show, Integer>{

}
