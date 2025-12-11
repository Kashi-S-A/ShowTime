package com.showtime.bookingservice.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer movie_id;
	
	private String title;
	private String duration;
	private String languages;
	private String genres;
	private String formats;
	
	@OneToMany
	private List<Group> group;
	
}
