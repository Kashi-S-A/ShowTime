package com.showtime.movie_service.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer movieId;
		
		private String title;
		private String duration;
		private String languages;
		private String genres;
		private String formats;
		
		@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		private List<Group> group;
		
}
