package com.showtime.movie_service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showtime.movie_service.dto.GroupDto;
import com.showtime.movie_service.dto.MovieRequestDto;
import com.showtime.movie_service.dto.MovieResponseDto;
import com.showtime.movie_service.entity.Group;
import com.showtime.movie_service.entity.Movie;
import com.showtime.movie_service.repository.MovieRepository;
import com.showtime.movie_service.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepository;
	
	
	public Movie toEntity(MovieRequestDto dto) {
		Movie movie = new Movie();
		BeanUtils.copyProperties(dto, movie);
		return movie;
		
	}
	
	public MovieResponseDto toDto(Movie movie) {
		MovieResponseDto dto = new MovieResponseDto();
		BeanUtils.copyProperties(movie, dto);
		return dto;
		
	}


	@Override
	public MovieResponseDto createMovie(MovieRequestDto request) {
		Movie movie = toEntity(request);
		movie.setMovieId(null);
		Movie saved = movieRepository.save(movie);
		return toDto(saved);
	} 
	
	@Override
	public MovieResponseDto getMovieById(Integer id) {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
		return toDto(movie);
	}

	@Override
	public List<MovieResponseDto> getAllMovies() {
		List<Movie> movies =  movieRepository.findAll();
		List<MovieResponseDto> dtos = new ArrayList<>();
		for(Movie movie: movies) {
			dtos.add(toDto(movie));
	}
		return dtos;
		}

	@Override
	public MovieResponseDto updateMovie(Integer id, MovieRequestDto request) {
		Movie existing = movieRepository.findById(id).orElseThrow(() -> new RuntimeException( "Movie not found with id: " + id));
		
//		existing.setTitle(movie.getTitle());
//		existing.setDuration(movie.getDuration());
//		existing.setLanguages(movie.getLangauges());
//		existing.setGenres(movie.getGenres());
//		existing.setFormats(movie.getFormats());
		
		BeanUtils.copyProperties(request, existing); //source, Target
		Movie updated = movieRepository.save(existing);
		return toDto(updated);
	}

	@Override
	public List<MovieResponseDto> searchByTitle(String title) {
	 List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
	 List<MovieResponseDto> dtos = new ArrayList<>();
	 for (Movie movie : movies) {
	        dtos.add(toDto(movie));
	    }
	    return dtos;
	}	
	
	@Override
	public List<MovieResponseDto> searchByLanguage(String language) {
	    List<Movie> movies = movieRepository.findByLanguagesContainingIgnoreCase(language);
	    List<MovieResponseDto> dtos = new ArrayList<>();
	    for (Movie movie : movies) {
	        dtos.add(toDto(movie));
	    }
	    return dtos;
	}

	@Override
	public List<MovieResponseDto> searchByGenre(String genre) {
	    List<Movie> movies = movieRepository.findByGenresContainingIgnoreCase(genre);
	    List<MovieResponseDto> dtos = new ArrayList<>();
	    for (Movie movie : movies) {
	        dtos.add(toDto(movie));
	    }
	    return dtos;
	}

	@Override
	public void deleteMovie(Integer id) {
		Movie movie = movieRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
		    movieRepository.delete(movie);
		
	}
	
}
