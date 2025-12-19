package com.showtime.screen_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeatRowDTO {
	private String rowLabel;
    private List<SeatCellDTO> cells;
}
//Row-level data
// for a row the data