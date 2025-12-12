package com.showtime.theatre_service.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Theatre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tid;
	
	private String tname;
	
	private String address;
	
	private String city;
	
	private Integer pincode;
	
	private String doc;
	
	@Enumerated(EnumType.STRING)
	private TheaterReqStatus status =TheaterReqStatus.PENDING;
	
	private String theatreRejectionReason;
	
	@CreatedDate
	@Column(name = "created_date_time", nullable = false, updatable = false)
	private LocalDateTime createdDateTime;

	@LastModifiedDate
	@Column(name = "updated_date_time", nullable = false)
	private LocalDateTime updatedDateTime;

	
	@ManyToOne
    @JoinColumn(name ="taid")
	@OnDelete(action = OnDeleteAction.CASCADE)
    private TheatreAdmin theatreAdmin;
    
    

    @ManyToOne
    @JoinColumn(name = "said")
    private SuperAdmin  superAdmin;
    
    @AssertTrue(message = "Rejection reason is required when status is REJECTED")
    public boolean isTheatreRejectionReasonValid() {
        if (status == TheaterReqStatus.REJECTED) {
            return theatreRejectionReason != null && !theatreRejectionReason.trim().isEmpty();
        }
        theatreRejectionReason = null;
        return true;
    }
    
    //getter setter

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
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

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public TheaterReqStatus getStatus() {
		return status;
	}

	public void setStatus(TheaterReqStatus status) {
		this.status = status;
	}

	public String getTheatreRejectionReason() {
		return theatreRejectionReason;
	}

	public void setTheatreRejectionReason(String theatreRejectionReason) {
		this.theatreRejectionReason = theatreRejectionReason;
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

	public TheatreAdmin getTheatreAdmin() {
		return theatreAdmin;
	}

	public void setTheatreAdmin(TheatreAdmin theatreAdmin) {
		this.theatreAdmin = theatreAdmin;
	}

	public SuperAdmin getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(SuperAdmin superAdmin) {
		this.superAdmin = superAdmin;
	}
}
