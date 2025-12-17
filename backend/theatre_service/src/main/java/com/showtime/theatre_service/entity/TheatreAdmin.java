package com.showtime.theatre_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;

@Entity
public class TheatreAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer taid;
	
	private String name;
	
	private String email;
	
	private String address;
	
	private String city;
	
	private String doc;
	
	@Enumerated(EnumType.STRING)  
	private TheatreAdminReqStatus status = TheatreAdminReqStatus.PENDING;
	
	private String rejectionReason;
	
	private String password;
	
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDateTime;
    
	@LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDateTime;
	
	@ManyToOne
	@JoinColumn(name="said" )
	private SuperAdmin superAdmin;
	
	@OneToMany(mappedBy = "theatreAdmin", cascade = CascadeType.ALL)
	private List<Theatre> theatre;
	
	@AssertTrue(message = "Rejection reason is required when status is REJECTED")
    public boolean isRejectionReasonValid() {
        if (status == TheatreAdminReqStatus.REJECTED) {
            return rejectionReason != null && !rejectionReason.trim().isEmpty();
        }
        return true;
    }


	public Integer getTaid() {
		return taid;
	}


	public void setTaid(Integer taid) {
		this.taid = taid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getDoc() {
		return doc;
	}


	public void setDoc(String doc) {
		this.doc = doc;
	}


	public TheatreAdminReqStatus getStatus() {
		return status;
	}


	public void setStatus(TheatreAdminReqStatus status) {
		this.status = status;
	}


	public String getRejectionReason() {
		return rejectionReason;
	}


	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}


	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}


	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}


	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}


	public SuperAdmin getSuperAdmin() {
		return superAdmin;
	}


	public void setSuperAdmin(SuperAdmin superAdmin) {
		this.superAdmin = superAdmin;
	}


	public List<Theatre> getTheatre() {
		return theatre;
	}


	public void setTheatre(List<Theatre> theatre) {
		this.theatre = theatre;
	}
	
	
}
