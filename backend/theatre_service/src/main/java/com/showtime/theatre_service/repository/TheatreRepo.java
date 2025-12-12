package com.showtime.theatre_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.theatre_service.entity.TheaterReqStatus;
import com.showtime.theatre_service.entity.Theatre;
import com.showtime.theatre_service.entity.TheatreAdmin;

public interface TheatreRepo extends JpaRepository<Theatre, Integer> {
	
	 List<Theatre> findByTheatreAdmin(TheatreAdmin theatreAdmin);
	 Page<Theatre> findByStatus(TheaterReqStatus status, Pageable pageable);
	 
}
