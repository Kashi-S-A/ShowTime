package com.showtime.bookingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.bookingservice.exception.ResourceNotFoundException;
import com.showtime.bookingservice.model.Seat;
import com.showtime.bookingservice.model.SeatStatus;
import com.showtime.bookingservice.model.Show;
import com.showtime.bookingservice.repo.SeatRepo;
import com.showtime.bookingservice.repo.SeatStatusRepo;
import com.showtime.bookingservice.repo.ShowRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
	
	private final SeatStatusRepo seatStatusRepo;
	private final SeatRepo seatRepo;
	private final ShowRepo showRepo;

	 // Get seat status for a seat + show
    @GetMapping("/status/{seatId}/{showId}")
    public SeatStatus getSeatStatus(
            @PathVariable Integer seatId,
            @PathVariable Integer showId) {

        Seat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        return seatStatusRepo.findBySeatAndShow(seat, show)
                .orElseThrow(() -> new ResourceNotFoundException("Seat status not found"));
    }
}
