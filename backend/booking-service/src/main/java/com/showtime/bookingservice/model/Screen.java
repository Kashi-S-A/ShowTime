package com.showtime.bookingservice.model;

import java.time.LocalTime;

import com.showtime.bookingservice.enums.FormatSupported;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Screen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer screen_id;
	@ManyToOne
	@JoinColumn(name="theatre_id")
	private Theatre theatre;
	
	private LocalTime screen_time;
	@Enumerated(EnumType.STRING)
	private FormatSupported format_supported;
	

}
