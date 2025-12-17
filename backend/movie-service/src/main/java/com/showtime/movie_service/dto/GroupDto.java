package com.showtime.movie_service.dto;

import lombok.Data;

@Data
public class GroupDto {
	private Integer group_id;
	private String name;
	private String role;
	private String image_url;
	private String type;
}
