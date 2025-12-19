package com.showtime.screen_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.screen_service.entity.Screen;

import java.util.List;

public interface ScreenRepo extends JpaRepository<Screen, Integer> {

	Screen findByScreenName(String screenName);
	List<Screen> findByTheatre_TheatreId(Integer theatreId);

}
