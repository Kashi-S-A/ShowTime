package com.showtime.screen_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.screen_service.entity.SeatLayout;

public interface seatLayoutRepo extends JpaRepository<SeatLayout, Integer> {

	List<SeatLayout> findByScreenId(Integer screenId);

}
