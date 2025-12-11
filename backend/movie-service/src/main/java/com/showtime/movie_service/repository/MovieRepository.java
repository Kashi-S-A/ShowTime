package com.showtime.movie_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.showtime.movie_service.entity.Movie;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	List<Movie> findByTitle(String title);
	
	List<Movie> findByTitleContainingIgnoreCase(String Title);
	
	List<Movie> findByLanguagesContainingIgnoreCase(String Language);
	
	List<Movie> findByGenresContainingIgnoreCase(String genre);
	
	
}
