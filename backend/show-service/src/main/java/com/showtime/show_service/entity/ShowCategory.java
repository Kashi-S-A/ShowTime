package com.showtime.show_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ShowCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer showcategoryId;
	
	private Integer category_id;
	private String category_name;
	private double price;
}
