package com.showtime.movie_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.movie_service.entity.Group;
import com.showtime.movie_service.enums.MovieType;

public interface GroupRepository extends JpaRepository<Group, Integer>{
	
	List<Group> findByMovieMovieId(Integer movieId); //It will give me list of group with all actors/crew for movie
	
	List<Group> findByMovieMovieIdAndType(Integer movieId, MovieType Type); //It will give me list of all cast members for specific movie id
	

}
