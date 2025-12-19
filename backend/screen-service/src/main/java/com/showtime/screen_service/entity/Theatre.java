package com.showtime.screen_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Theatre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer theatreId;
	String theatre_name;
	String address;
	String city;
	Integer pincode;
	String loc;
	String theatre_status;
	String rejectReason;
	
	
	@CreationTimestamp
	@Column(updatable = false)
	LocalDateTime createdDateTime;
	
	@UpdateTimestamp
	LocalDateTime updatedDateTime;
	
	@OneToMany(mappedBy = "theatre")
	List<Screen> screen;
	
	
}
