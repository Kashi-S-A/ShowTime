package com.showtime.movie_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.showtime.movie_service.dto.MovieRequestDto;
import com.showtime.movie_service.dto.MovieResponseDto;
import com.showtime.movie_service.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    
    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@RequestBody MovieRequestDto request) {
        MovieResponseDto dto = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovie(@PathVariable Integer id) {
        try {
            MovieResponseDto dto = movieService.getMovieById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // get all movies
    @GetMapping
    public List<MovieResponseDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(@PathVariable Integer id,
                                                        @RequestBody MovieRequestDto request) {
        try {
            MovieResponseDto updated = movieService.updateMovie(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Search By title
    @GetMapping("/search/title")
    public List<MovieResponseDto> searchByTitle(@RequestParam String title) {
        return movieService.searchByTitle(title);
    }

    // Search by langauge
    @GetMapping("/search/language")
    public List<MovieResponseDto> searchByLanguage(@RequestParam String language) {
        return movieService.searchByLanguage(language);
    }

    // Search by genre
    @GetMapping("/search/genre")
    public List<MovieResponseDto> searchByGenre(@RequestParam String genre) {
        return movieService.searchByGenre(genre);
    }
}
