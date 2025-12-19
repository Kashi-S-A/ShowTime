package com.showtime.screen_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.screen_service.dto.SeatLayoutDTO;
import com.showtime.screen_service.dto.SeatRowDTO;
import com.showtime.screen_service.service.SeatService;

@RestController
@RequestMapping("/api/seat-layout")
public class SeatController {

    @Autowired
    private  SeatService seatLayoutService;
    
    @PostMapping("/create")
    public ResponseEntity<String> createSeatLayout(@RequestBody SeatLayoutDTO dto) {

        seatLayoutService.createSeatLayout(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Seat layout created successfully");
    }
    
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<SeatRowDTO>> getSeatLayout(@PathVariable Integer screenId) {

        List<SeatRowDTO> response = seatLayoutService.getSeatLayout(screenId);

        return ResponseEntity.ok(response);
    }
}
