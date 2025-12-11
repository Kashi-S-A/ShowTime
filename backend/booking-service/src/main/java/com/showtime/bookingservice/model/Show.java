package com.showtime.bookingservice.model;


import java.time.LocalDate;
import java.time.LocalTime;

import com.showtime.bookingservice.enums.FormatSupported;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer show_id;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@OneToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;
	
	private LocalDate show_date;
	
	private LocalTime show_time;
	
	private String languages;
	
//	private double pricePerSeat;
}
