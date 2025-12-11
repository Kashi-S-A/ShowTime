package com.showtime.show_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.showtime.show_service.entity.Show;

import java.util.List;
import java.time.LocalDate;


@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

	List<Show> findByShowDate(LocalDate show_date);
	
	Show findByShowId(Integer show_id);
	
	void deleteByShowId(Integer show_id);
	
	void deleteByShowDate(LocalDate show_date);
	
}
