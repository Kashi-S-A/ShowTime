package com.showtime.movie_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class MovieRequestDto {
	
		private String title;
		private String duration;
		private String languages;
		private String genres;
		private String formats;
		private List<GroupDto> group;

}
