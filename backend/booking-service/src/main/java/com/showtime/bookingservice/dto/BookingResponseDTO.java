package com.showtime.bookingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import com.showtime.bookingservice.enums.BookingStatus;
import com.showtime.bookingservice.model.BookingSeat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Data
public class BookingResponseDTO {

	private Integer bookingId;
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	private Double amount;
	private String orderId;
	
    
}
