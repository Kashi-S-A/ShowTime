package com.showtime.show_service.entity;

import com.showtime.show_service.enums.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="Groups")
@Data
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long group_id;
	private String group_name;
	private String role;
	private String image_url;
	private Type type;
	
}
