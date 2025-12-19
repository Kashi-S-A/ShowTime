package com.showtime.screen_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.screen_service.dto.ScreenDTO;
import com.showtime.screen_service.dto.ScreenResponseDTO;
import com.showtime.screen_service.service.ScreenService;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {
		@Autowired
	    private ScreenService screenService;
		

		@PostMapping("/create")
	    public ResponseEntity<String> createScreen(@RequestBody ScreenDTO dto) {
	    	screenService.createScreen(dto);
	        return ResponseEntity.ok("Screen created successfully");
	    }
	

		@GetMapping("/theatre/{theatreId}")
	    public List<ScreenResponseDTO> getScreensByTheatre(
	            @PathVariable Integer theatreId) {

	        return screenService.getScreensByTheatre(theatreId);
	    }
}
