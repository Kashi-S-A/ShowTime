package com.showtime.theatre_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.showtime.theatre_service.dto.AdminApprovalRequestDTO;
import com.showtime.theatre_service.entity.SuperAdmin;
import com.showtime.theatre_service.entity.TheaterReqStatus;
import com.showtime.theatre_service.entity.Theatre;
import com.showtime.theatre_service.entity.TheatreAdmin;
import com.showtime.theatre_service.entity.TheatreAdminReqStatus;
import com.showtime.theatre_service.repository.SuperAdminRepo;
import com.showtime.theatre_service.repository.TheatreAdminRepo;
import com.showtime.theatre_service.repository.TheatreRepo;

import jakarta.transaction.Transactional;

@Service
public class SuperAdminService {
	
	@Autowired
	private SuperAdminRepo superAdminRepo;
	
	@Autowired
	private TheatreAdminRepo theatreAdminRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TheatreRepo theatreRepo;
	
	//login
	public SuperAdmin login(String email, String password) {
		SuperAdmin sa = superAdminRepo.findByEmail(email)
				.orElseThrow(()->new RuntimeException("Super admin not found"));
		
		if(!sa.getPassword().equals(password)) {
			throw new RuntimeException("Invalid password");
		}
		return sa;
	}
	
	//pending request of theatreAdmin
	public Page<TheatreAdmin> getPendingTheatreAdmin(Integer page, Integer size, String sortBy, String SortDir){
		Sort sort = SortDir.equalsIgnoreCase("desc")?
				Sort.by(sortBy).descending():
				Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return theatreAdminRepo.findByStatus(TheatreAdminReqStatus.PENDING, pageable);
	}
	
	//approve request of theatreAdmin
	public TheatreAdmin approveTheatreAdmin(Integer taid,Integer said) {
		TheatreAdmin admin = theatreAdminRepo.findById(taid)
				.orElseThrow(()-> new RuntimeException("TheatreAdmin not found"));
		SuperAdmin sa =superAdminRepo.findById(said)
				.orElseThrow(()-> new RuntimeException("SuperAdmin not found"));
		admin.setStatus(TheatreAdminReqStatus.APPROVED);
	    admin.setRejectionReason(null);
		admin.setSuperAdmin(sa);
		String password = generateRandomPassword(8);
	    admin.setPassword(password);

	    theatreAdminRepo.save(admin);
	    
	    emailService.sendApprovalEmail(
	            admin.getEmail(),
	            admin.getName(),
	            admin.getEmail(),
	            password
	    );
	    
		return admin;
	}
	
	//reject theatreAdmin
	@Transactional
	public TheatreAdmin rejectTheatreAdmin(Integer said,Integer taid, AdminApprovalRequestDTO dto) {
	    TheatreAdmin admin = theatreAdminRepo.findById(taid)
	            .orElseThrow(() -> new RuntimeException("TheatreAdmin not found"));
	    
	    SuperAdmin sa = superAdminRepo.findById(said)
	    	       .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

	    admin.setStatus(TheatreAdminReqStatus.REJECTED);
	    admin.setRejectionReason(dto.getRejectionReason());
	    admin.setSuperAdmin(sa);
	    admin.setUpdatedDateTime(LocalDateTime.now());
	    
	    theatreAdminRepo.save(admin);

	    
	    emailService.sendRejectionEmail(
	            admin.getEmail(),
	            admin.getName(),
	            dto.getRejectionReason()
	    );

	    return admin;
	}
	
	//to genrate Random password
	@Transactional
	private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%!";
        Random random = new Random();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
	}
	//pending theatre
	public Page<Theatre> getPendingTheatres(Integer page, Integer size, String sortBy, String sortDir) {

	    Sort sort = sortDir.equalsIgnoreCase("desc") ?
	            Sort.by(sortBy).descending() :
	            Sort.by(sortBy).ascending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    return theatreRepo.findByStatus(TheaterReqStatus.PENDING, pageable);
	}
	
	//approve theatre
	public Theatre approveTheatre(Integer tid, Integer said) {

	    

	    Theatre theatre = theatreRepo.findById(tid)
	            .orElseThrow(() -> new RuntimeException("Theatre not found"));
	    SuperAdmin sa = superAdminRepo.findById(said)
	            .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

	   

	    theatre.setStatus(TheaterReqStatus.APPROVED);
	    theatre.setTheatreRejectionReason(null);
	    theatre.setSuperAdmin(sa);
	    theatre.setTheatreRejectionReason(null);

	    Theatre saved = theatreRepo.save(theatre);
	    
	    emailService.sendTheatreApprovalEmail(
	            theatre.getTheatreAdmin().getEmail(),
	            theatre.getTheatreAdmin().getName(),
	            theatre.getTname()
	    );

	    
	    return saved;
	}

	 
	 //rejected theatre
	 public Theatre rejectTheatre(Integer tid,Integer said,AdminApprovalRequestDTO dto) {
	        Theatre theatre = theatreRepo.findById(tid)
	                .orElseThrow(() -> new RuntimeException("Theatre not found"));
	        SuperAdmin sa = superAdminRepo.findById(said)
		            .orElseThrow(() -> new RuntimeException("SuperAdmin not found"));

	        theatre.setStatus(TheaterReqStatus.REJECTED);
	        theatre.setTheatreRejectionReason(dto.getRejectionReason());
	        theatre.setSuperAdmin(sa);
	        Theatre saved = theatreRepo.save(theatre);

	        
	        emailService.sendTheatreRejectionEmail(
	                theatre.getTheatreAdmin().getEmail(),
	                theatre.getTheatreAdmin().getName(),
	                theatre.getTname(),
	                dto.getRejectionReason()
	        );

	        return saved;
	    }
}
