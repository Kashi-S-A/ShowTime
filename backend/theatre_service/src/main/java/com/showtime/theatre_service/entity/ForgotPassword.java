package com.showtime.theatre_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ForgotPassword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fid;
	
	@Column(nullable = false)
	private String otp;
	
	@Column(nullable = false)
	private LocalDateTime expiryTime;
	
	@Column(nullable = false)
    private boolean used = false;
	
	@ManyToOne
	@JoinColumn(name = "taid", nullable = false)
	private TheatreAdmin theatreAdmin;
	
	public ForgotPassword() {
	    
	}

	
	public ForgotPassword(String otp, LocalDateTime expiryTime, TheatreAdmin theatreAdmin) {
	    this.otp = otp;
	    this.expiryTime = expiryTime;
	    this.theatreAdmin = theatreAdmin;
	}


	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public TheatreAdmin getTheatreAdmin() {
		return theatreAdmin;
	}

	public void setTheatreAdmin(TheatreAdmin theatreAdmin) {
		this.theatreAdmin = theatreAdmin;
	}
	
	
}
