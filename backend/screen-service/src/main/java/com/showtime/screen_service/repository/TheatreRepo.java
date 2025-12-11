package com.showtime.screen_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.screen_service.entity.Theatre;

@Repository
public interface TheatreRepo extends JpaRepository<Theatre, Integer> {

	public Theatre findByTheatre_name(String theatre_name);
}
