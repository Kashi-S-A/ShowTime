package com.showtime.screen_service.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Theatre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long theatre_id;
	private String theatre_name;
	private String address;
	private String city;
	private Integer pincode;
	private String loc; 
	private String theatre_status;
	private String rejectReason;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDateTime;
	@UpdateTimestamp
	private LocalDateTime updatedDateTime;
}
