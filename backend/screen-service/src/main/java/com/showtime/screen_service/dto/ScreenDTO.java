package com.showtime.screen_service.dto;

import java.util.List;

import com.showtime.screen_service.entity.Category;
import com.showtime.screen_service.entity.FormatSupported;
import lombok.Data;

@Data
public class ScreenDTO {

	private String screenName;
	private Integer theater_id;
	private String format_supported;
	private List<Category> category;
}
