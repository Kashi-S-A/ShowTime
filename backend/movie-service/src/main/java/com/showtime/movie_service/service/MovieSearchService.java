package com.showtime.movie_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.showtime.movie_service.dto.TheatreDto;


public interface MovieSearchService {
	
	List<TheatreDto> searchByCity(String city);
	
	List<TheatreDto> searchByTheaterName(String theatreName);

}
