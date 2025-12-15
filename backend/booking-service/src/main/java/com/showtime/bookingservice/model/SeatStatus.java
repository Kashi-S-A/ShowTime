package com.showtime.bookingservice.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.showtime.bookingservice.enums.StatusSeat;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class SeatStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seat_status_id;
	
	private String status;
	
	@Column(name = "locked_until")
	private LocalDateTime Locked_until;
	
	@ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
	
	@ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
	
	@ManyToOne
    @JoinColumn(name = "locked_by_user_id")
	private User lockedByUser;

}
