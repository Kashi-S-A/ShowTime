package com.showtime.show_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Theatre {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long theatre_id;
	
	private String theatre_name;
	private String address;
	private String city;
	private Integer pincode;
	private String loc;
	private String theatre_status;
	private String rejectReason;
	private LocalDateTime createdDateTime;
	private LocalDateTime updatedDateTime;
	
	@Autowired
	@ManyToMany
	List<Movie> movie;
	
	@Autowired
	@OneToMany
	List<Screen> screen;
	
}
