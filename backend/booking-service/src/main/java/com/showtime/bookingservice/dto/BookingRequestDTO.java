package com.showtime.bookingservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingRequestDTO {
	
	private Integer user_id;
	private Integer show_id;
	private List<Integer> seat_id;

}
