package com.showtime.screen_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.screen_service.entity.Screen;
import com.showtime.screen_service.entity.Seat;


public interface SeatRepo extends JpaRepository<Seat, Integer>{

	boolean existsByScreenAndRowLabelAndSeatNumber(Screen screen, String rowLabel,Integer seatNumber);

	List<Seat> findByScreen_ScreenId(Integer screenId);
}
