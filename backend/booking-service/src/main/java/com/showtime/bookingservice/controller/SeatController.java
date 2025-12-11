package com.showtime.bookingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.bookingservice.model.SeatStatus;
import com.showtime.bookingservice.repo.SeatStatusRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
	
	private final SeatStatusRepo seatStatusRepo;

	    // ✔ Check seat status for a particular show
	    @GetMapping("/status/{seatId}/{showId}")
	    public SeatStatus getSeatStatus(
	            @PathVariable Integer seatId,
	            @PathVariable Integer showId) {

	        return seatStatusRepo.findBySeatAndShow(seatId, showId)
	                .orElseThrow(() -> new RuntimeException("Seat status not found"));
	    }
}
