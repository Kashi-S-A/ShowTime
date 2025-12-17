package com.showtime.movie_service.dto;

import lombok.Data;

@Data
public class GroupRequestDto {
    private String name;
    private String role;
    private String image_url;
    private String type;        // Creaw or cast
    private Integer movieId;   
}
