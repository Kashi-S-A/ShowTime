package com.showtime.screen_service.dto;

import lombok.Data;

@Data
public class ScreenResponseDTO {
	
    private Integer screenId;
    private String screenName;
    private String formatSupported;
}
