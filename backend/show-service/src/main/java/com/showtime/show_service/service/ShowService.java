package com.showtime.show_service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.showtime.show_service.entity.Show;

public interface ShowService {

	 ResponseEntity<String> addShow(Show show);
	 
	 ResponseEntity<String> updateShow(Show show);
	 
	 ResponseEntity<String> deleteByShowId(Integer show_id);
	 
	 ResponseEntity<String> deleteByShowDate(LocalDate show_date);
	 
	 List<Show> findByShowDate(LocalDate show_date,Long screen_id);
	 
	 List<Show> findByShowDate(LocalDate show_date);
	
}
