package com.showtime.movie_service.service;

import java.util.List;

import com.showtime.movie_service.dto.MovieRequestDto;
import com.showtime.movie_service.dto.MovieResponseDto;
import com.showtime.movie_service.entity.Movie;

public interface MovieService {
	
	    MovieResponseDto createMovie(MovieRequestDto request);
	    
	    MovieResponseDto getMovieById(Integer id);
	    
	    List<MovieResponseDto> getAllMovies();
	    
	    MovieResponseDto updateMovie(Integer id, MovieRequestDto request);
	    
	    List<MovieResponseDto> searchByTitle(String title);
	    
	    List<MovieResponseDto> searchByLanguage(String language);
	    
	    List<MovieResponseDto> searchByGenre(String genre);


	
	}

	

