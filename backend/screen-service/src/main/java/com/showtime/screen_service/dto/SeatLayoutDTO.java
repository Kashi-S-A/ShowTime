package com.showtime.screen_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeatLayoutDTO {
	
    private Integer screenId;
    private String rowLabel;
    private List<Integer> layout;
    private Integer categoryId;

}
