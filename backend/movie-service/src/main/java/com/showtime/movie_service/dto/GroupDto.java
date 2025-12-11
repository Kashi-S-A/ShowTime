package com.showtime.movie_service.dto;

import lombok.Data;

@Data
public class GroupDto {
	private Integer id;
	private String name;
	private String role;
	private String imageUrl;
	private String type;
}
