package com.showtime.screen_service.dto;

import lombok.Data;

@Data
public class SeatDTO {
	
	String row_label;
	Integer start_seat;
	Integer end_seat;
	String category_name; //categoryName
	String screen_name;

}
