package com.showtime.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.showtime.bookingservice.enums.StatusSeat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class BookingSeat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer bookingseat_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
	
	private Double amount;
	
	@ManyToOne
    @JoinColumn(name = "seat_status_id")
    private SeatStatus status;

}
