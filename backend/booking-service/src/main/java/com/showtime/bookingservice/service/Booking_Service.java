package com.showtime.bookingservice.service;

import java.util.List;

import com.showtime.bookingservice.dto.BookingRequestDTO;
import com.showtime.bookingservice.dto.BookingResponseDTO;
import com.showtime.bookingservice.model.Booking;

public interface Booking_Service {

	BookingResponseDTO createBooking(BookingRequestDTO request);
	
	BookingResponseDTO getBookingById(Integer bookingId);
	
	List<BookingResponseDTO> getAllBookings();
	
	List<BookingResponseDTO> getBookingsByUserId(Integer userId);
	
	void cancelBooking(Integer bookingId);
	
 
}


