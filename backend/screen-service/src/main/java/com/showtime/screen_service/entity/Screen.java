package com.showtime.screen_service.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Screen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long screen_id;
	private Theatre theatre;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime screen_created_time;
	private FormatSupported format_supported;
	
	@OneToMany(mappedBy = "screen")
	private List<Category> category;
}