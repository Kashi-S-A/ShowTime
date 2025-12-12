package com.showtime.theatre_service.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class SuperAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer said;
	
	private String email;
	
	private String password;
	
	@OneToMany(mappedBy="superAdmin")
	private List<TheatreAdmin> theatreAdmin;
	
	@OneToMany(mappedBy = "superAdmin")
    private List<Theatre> theatres;

	public Integer getSaid() {
		return said;
	}

	public void setSaid(Integer said) {
		this.said = said;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TheatreAdmin> getTheatreAdmin() {
		return theatreAdmin;
	}

	public void setTheatreAdmin(List<TheatreAdmin> theatreAdmin) {
		this.theatreAdmin = theatreAdmin;
	}

	public List<Theatre> getTheatres() {
		return theatres;
	}

	public void setTheatres(List<Theatre> theatres) {
		this.theatres = theatres;
	}
	
	
}
