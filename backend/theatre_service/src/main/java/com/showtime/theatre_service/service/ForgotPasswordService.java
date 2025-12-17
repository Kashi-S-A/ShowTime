package com.showtime.theatre_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.showtime.theatre_service.entity.ForgotPassword;
import com.showtime.theatre_service.entity.TheatreAdmin;
import com.showtime.theatre_service.repository.ForgotPasswordRepo;
import com.showtime.theatre_service.repository.TheatreAdminRepo;

import jakarta.transaction.Transactional;

@Service
public class ForgotPasswordService {
	 
	@Autowired
	private TheatreAdminRepo theatreAdminRepo;
	
	@Autowired
	private ForgotPasswordRepo forgotPasswordRepo;
	
	@Autowired
	private EmailService emailService;
	
	// SEND OTP
	public String sendOtp(String email) {

	    TheatreAdmin theatreAdmin = theatreAdminRepo.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Theatre admin not found"));

	    forgotPasswordRepo.deleteByTheatreAdmin(theatreAdmin);
	    
	    String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

	    ForgotPassword fp = new ForgotPassword(
	            otp,
	            LocalDateTime.now().plusMinutes(2),
	            theatreAdmin
	    );

	    forgotPasswordRepo.save(fp);

	    emailService.sendOtpMail(
	            theatreAdmin.getEmail(),
	            theatreAdmin.getName(),
	            otp
	    );

	    return "OTP sent successfully";
	}

	// VERIFY OTP
	 public String verifyOtp(String email, String otp) {
		 	
		 otp = otp.trim();
		 
	        TheatreAdmin admin = theatreAdminRepo.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("Admin not found"));

	        ForgotPassword fp = forgotPasswordRepo
	        	    .findTopByTheatreAdminAndOtpAndUsedFalseOrderByExpiryTimeDesc(admin, otp)
	        	    .orElseThrow(() -> new RuntimeException("Invalid OTP"));


	        if (fp.getExpiryTime().isBefore(LocalDateTime.now())) {
	            throw new RuntimeException("OTP expired");
	        }

	        fp.setUsed(true);
	        forgotPasswordRepo.save(fp);

	        return "OTP verified";
	    }
	 //RESET PASSWORD
	 public String resetPassword(
	            String email, String newPassword, String confirmPassword) {

	        if (!newPassword.equals(confirmPassword)) {
	            throw new RuntimeException("Passwords do not match");
	        }

	        TheatreAdmin admin = theatreAdminRepo.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("Admin not found"));

	        admin.setPassword(newPassword); //later encrypt
	        theatreAdminRepo.save(admin);

	        return "Password reset successful";
	    }
	 
	 	@Transactional
	    public void cleanup() {
	        forgotPasswordRepo.deleteByExpiryTimeBefore(LocalDateTime.now());
	    }
	 	
	 	@Scheduled(fixedRate = 120000)
	 	public void runCleanup() {
	 	    cleanup();
	 	}

}
