package com.showtime.bookingservice.model;

import java.util.List;

import com.showtime.bookingservice.enums.StatusSeat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Seat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seat_id")
	private Integer seatId;
	
	private String seatNumber;
	private String rowLabel;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;
	
	private boolean isBlocked;
	
	@Enumerated(EnumType.STRING)
	private StatusSeat status;

}
