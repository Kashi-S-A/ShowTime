package com.showtime.screen_service.entity;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Screen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 Integer screenId;

    String screenName;
    String formatSupported;
	
	@OneToMany(mappedBy = "screen")
	List<Category> category;

	
	@OneToMany(mappedBy = "screen")
	List<Seat> seat;

	
	@ManyToOne
	@JoinColumn(name = "theatre_id")
	Theatre theatre;
	
}
