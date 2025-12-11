package com.showtime.bookingservice.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.showtime.bookingservice.enums.TheatreStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Theatre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer theatre_id;
	
	private String theatre_name;
	private String address;
	private String city;
	private Integer pincode;
	private String loc;
	@Enumerated(EnumType.STRING)
	private TheatreStatus status;
	private String rejectReason;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdDateTime;
	
	@UpdateTimestamp
	private LocalDateTime updatedDateTime;
	
	
}
