package com.showtime.show_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.showtime.show_service.entity.Category;

import lombok.Data;

@Data
public class ShowDTO {

	private LocalDate show_date;
	private LocalTime show_starttime;
	private LocalTime show_endtime;
	private String languages;
	
	private String title;
	
	private Long screen_id;

	private List<Category> category;
}
