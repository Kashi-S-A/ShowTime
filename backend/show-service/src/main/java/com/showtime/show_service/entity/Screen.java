package com.showtime.show_service.entity;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Screen {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="screen_id")
	private Long screenId;
	
	private String screen_name;
	private LocalTime screen_time;
	private String format_supported;
	
	@Autowired
	@ManyToOne
	private Theatre theatre;
	
	@Autowired
	@OneToMany
	private List<Category> category;

}
