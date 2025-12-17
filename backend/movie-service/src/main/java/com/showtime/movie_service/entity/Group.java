package com.showtime.movie_service.entity;

import com.showtime.movie_service.enums.MovieType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "groups")
@Data
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String role;
	private String imageURL;
	
	@Enumerated(EnumType.STRING)
	private MovieType type;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	

}
