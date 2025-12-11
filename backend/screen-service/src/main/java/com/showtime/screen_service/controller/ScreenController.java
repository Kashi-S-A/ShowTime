package com.showtime.screen_service.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.screen_service.dto.ScreenDTO;
import com.showtime.screen_service.entity.Screen;
import com.showtime.screen_service.repository.TheatreRepo;

@RestController
public class ScreenController {
	
	@Autowired
	TheatreRepo theatreRepo;

	@PostMapping
	public String addScreen(@RequestBody ScreenDTO screenDTO) {
		
		Screen screen = new Screen();
		BeanUtils.copyProperties(screenDTO, screen);
		screen.setTheatre(theatreRepo.findByTheatre_name(screenDTO.getTheater_name()));
		
		return null;
	}

}
