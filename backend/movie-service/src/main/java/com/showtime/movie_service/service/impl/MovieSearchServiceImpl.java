package com.showtime.movie_service.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.showtime.movie_service.dto.TheatreDto;
import com.showtime.movie_service.service.MovieSearchService;

@Service

public class MovieSearchServiceImpl implements MovieSearchService{

	@Autowired
	private RestTemplate restTemplate;
	
	//Base Url of Theatre Micro Service
	private static final String THEATRE_BASE_URL = "https://localhost:8081";
	
	public MovieSearchServiceImpl(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}
	
	@Override
	public List<TheatreDto> searchByCity(String city) {
		String url = THEATRE_BASE_URL + "/theatres/city/{city}";
		TheatreDto[] response = restTemplate.getForObject(url, TheatreDto[].class, city);
		return response != null ? Arrays.asList(response) : List.of();
	}

	@Override
	public List<TheatreDto> searchByTheaterName(String theatreName) {
		String url = THEATRE_BASE_URL + "/theatres/search/{name}";
		TheatreDto[] response = restTemplate.getForObject(url, TheatreDto[].class, theatreName);
		return response != null ? Arrays.asList(response) : List.of();
	}
	
	

}
