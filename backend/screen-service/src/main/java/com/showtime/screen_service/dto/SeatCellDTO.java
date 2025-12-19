package com.showtime.screen_service.dto;

import com.showtime.screen_service.entity.Theatre_Status;

import lombok.Data;

@Data
public class SeatCellDTO {
	   private String type; // SEAT or GAP
	    private Integer seatNumber;
	    private Theatre_Status status;
}
//Cell-level data
//status of each cell