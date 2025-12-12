package com.showtime.theatre_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.showtime.theatre_service.dto.AddTheatreRequestDTO;
import com.showtime.theatre_service.dto.TheatreAdminRegisterRequestDTO;
import com.showtime.theatre_service.entity.TheaterReqStatus;
import com.showtime.theatre_service.entity.Theatre;
import com.showtime.theatre_service.entity.TheatreAdmin;
import com.showtime.theatre_service.entity.TheatreAdminReqStatus;
import com.showtime.theatre_service.repository.TheatreAdminRepo;
import com.showtime.theatre_service.repository.TheatreRepo;

@Service
public class TheatreAdminService {
	
	@Autowired
	private TheatreAdminRepo theatreAdminRepo;
	
	@Autowired
	private TheatreRepo theatreRepo;
	
	//theatreAdmin req
	public TheatreAdmin registerTheatreAdmin(TheatreAdminRegisterRequestDTO dto) {
	    TheatreAdmin admin = new TheatreAdmin();
	    BeanUtils.copyProperties(dto, admin);
	    
	   
	    admin.setStatus(TheatreAdminReqStatus.PENDING);
	    admin.setPassword(null); 
	    admin.setRejectionReason(null);
	    admin.setCreatedDateTime(LocalDateTime.now());
	    admin.setUpdatedDateTime(LocalDateTime.now());

	    return theatreAdminRepo.save(admin);
	}
	
	//Theatrereq
	public Theatre addTheatre(Integer taid,AddTheatreRequestDTO dto) {
		TheatreAdmin admin =theatreAdminRepo.findById(taid).orElseThrow(()->new RuntimeException("TheatreAdmin not found"));
		
		if (admin.getStatus() == TheatreAdminReqStatus.REJECTED) {
	        throw new RuntimeException("You are rejected by SuperAdmin — you cannot add theatre requests.");
	    }
		 if (admin.getStatus() == TheatreAdminReqStatus.PENDING) {
		     throw new RuntimeException("Your account is pending approval. You cannot add theatre requests yet.");
		}
		Theatre theatre = new Theatre();
        theatre.setTname(dto.getTname());
        theatre.setAddress(dto.getAddress());
        theatre.setCity(dto.getCity());
        theatre.setPincode(dto.getPincode());
        theatre.setDoc(dto.getDoc());
        theatre.setStatus(TheaterReqStatus.PENDING);
        theatre.setTheatreAdmin(admin);
        theatre.setSuperAdmin(null);
        
        return theatreRepo.save(theatre);
	}
	
	//login
	public TheatreAdmin login(String email, String password) {
		
		TheatreAdmin admin = theatreAdminRepo.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Admin not found"));

		
		if (admin.getStatus() == TheatreAdminReqStatus.REJECTED) {
	        throw new RuntimeException("Your account request was rejected by SuperAdmin. You cannot login.");
	    }
		
		if (admin.getStatus() == TheatreAdminReqStatus.PENDING) {
	        throw new RuntimeException("Your account is still pending approval. Please wait for SuperAdmin approval.");
	    }
		
		if (!admin.getPassword().equals(password)) {
	        throw new RuntimeException("Invalid password");
	    }
		
		return admin;
	}
	//fetch all theatre
	public Page<Theatre> getAllTheatre(Integer page, Integer size, String sortBy, String SortDir){
		Sort sort = SortDir.equalsIgnoreCase("desc")?
				Sort.by(sortBy).descending():
				Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return theatreRepo.findAll(pageable);
	}
	
	


}
