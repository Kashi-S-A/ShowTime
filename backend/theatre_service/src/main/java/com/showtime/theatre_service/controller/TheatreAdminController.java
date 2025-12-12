package com.showtime.theatre_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.theatre_service.dto.AddTheatreRequestDTO;
import com.showtime.theatre_service.dto.TheatreAdminRegisterRequestDTO;
import com.showtime.theatre_service.entity.Theatre;
import com.showtime.theatre_service.entity.TheatreAdmin;
import com.showtime.theatre_service.service.TheatreAdminService;

@RestController
@RequestMapping("/theatreAdmin")
public class TheatreAdminController {
	
	@Autowired
    private TheatreAdminService theatreAdminService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody TheatreAdminRegisterRequestDTO dto) {
	    try {
	        TheatreAdmin admin = theatreAdminService.registerTheatreAdmin(dto);
	        return ResponseEntity.ok(admin);   
	    } 
	    catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage()); 
	    }
	}

	
	@PostMapping("/addTheatre")
	public ResponseEntity<?> addTheatre(
	        @RequestParam Integer taid,
	        @RequestBody AddTheatreRequestDTO dto) {
	    try {
	        Theatre theatre = theatreAdminService.addTheatre(taid, dto);
	        return ResponseEntity.ok(theatre);  
	    } 
	    catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage()); 
	    }
	}

	
	@PostMapping("/login")
	public ResponseEntity<?> loginTheatreAdmin(
	        @RequestParam String email,
	        @RequestParam String password) {

	    try {
	        TheatreAdmin admin = theatreAdminService.login(email, password);
	        return ResponseEntity.ok(admin);

	    } catch (RuntimeException ex) {
	        return ResponseEntity
	                .badRequest()
	                .body(Map.of("error", ex.getMessage()));
	    }
	}
	
	@GetMapping("/allTheatres")
	public ResponseEntity<?> getAllTheatres(
	        @RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "10") Integer size,
	        @RequestParam(defaultValue = "tid") String sortBy,
	        @RequestParam(defaultValue = "asc") String sortDir
	) {
	    try {
	        Page<Theatre> theatrePage = theatreAdminService.getAllTheatre(page, size, sortBy, sortDir);
	        return ResponseEntity.ok(theatrePage);
	        
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(500)
	                .body("Something went wrong while fetching theatres: " + e.getMessage());
	    }
	}

	
}
