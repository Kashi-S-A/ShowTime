package com.showtime.show_service.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Show {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="show_id")
	private Integer showId;
	
	@Column(name="show_date")
	private LocalDate showDate;
	private LocalTime show_starttime;
	private LocalTime show_endtime;
	private String language;
	
	@Autowired
	@ManyToOne
	private Movie movie;
	
	@Autowired
	@ManyToOne
	private Screen screen;
	
	@Autowired
	@OneToMany
	private List<ShowCategory> showCategory;
	
}
