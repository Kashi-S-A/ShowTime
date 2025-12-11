package com.showtime.bookingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.bookingservice.dto.BookingRequestDTO;
import com.showtime.bookingservice.dto.BookingResponseDTO;
import com.showtime.bookingservice.service.Booking_Service;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

	 private final Booking_Service bookingService;
	 
	  // 1️- Create a booking
	    @PostMapping("/create")
	    public ResponseEntity<BookingResponseDTO> createBooking(
	            @RequestBody BookingRequestDTO bookingRequest) {

	        BookingResponseDTO response = bookingService.createBooking(bookingRequest);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    
	 // 2️- Get booking by ID
	    @GetMapping("/{bookingId}")
	    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Integer bookingId) {
	        BookingResponseDTO booking = bookingService.getBookingById(bookingId);
	        return new ResponseEntity<>(booking, HttpStatus.OK);
	    }
   

	    // 3️- Get all bookings
	    @GetMapping
	    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
	        List<BookingResponseDTO> bookings = bookingService.getAllBookings();
	        return new ResponseEntity<>(bookings, HttpStatus.OK);
	    }
	    
	 // 4 - Get bookings by user ID
	    @GetMapping("/user/{userId}")
	    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUserId(@PathVariable Integer userId) {
	        List<BookingResponseDTO> bookings = bookingService.getBookingsByUserId(userId);
	        return ResponseEntity.ok(bookings);
	    }
	    
	    // 5- Cancel a booking
	    @DeleteMapping("/{bookingId}/cancel")
	    public ResponseEntity<String> cancelBooking(@PathVariable Integer bookingId) {
	        bookingService.cancelBooking(bookingId);
	        return new ResponseEntity<>("Booking cancelled successfully", HttpStatus.OK);
	    }
}

