package com.showtime.bookingservice.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer payment_id;
	@OneToOne
	private Booking booking_id;
	private String razorpay_order_id;
	private double amount;
	private String status;
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime created_at;

}
