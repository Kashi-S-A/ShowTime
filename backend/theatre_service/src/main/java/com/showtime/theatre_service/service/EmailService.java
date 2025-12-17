package com.showtime.theatre_service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.showtime.theatre_service.util.EmailUtil;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	 	@Autowired
	    private JavaMailSender mailSender;

	    @Autowired
	    private EmailUtil emailUtil;
	    
	    @Autowired
	    private SpringTemplateEngine templateEngine;


	    
	    public void sendApprovalEmail(String to, String name, String email, String password) {
	        Map<String, Object> model = new HashMap<>();
	        model.put("name", name);
	        model.put("email", email);
	        model.put("password", password);

	        emailUtil.sendEmail(to, "Your Theatre Admin Account is Approved", "approval_email", model);
	    }

	    
	    public void sendRejectionEmail(String to, String name, String reason) {
	        Map<String, Object> model = new HashMap<>();
	        model.put("name", name);
	        model.put("reason", reason);

	        emailUtil.sendEmail(to, "Your Theatre Admin Registration is Rejected", "rejection_email", model);
	    }
	 // THEATRE APPROVAL EMAIL
	    public void sendTheatreApprovalEmail(String to, String name, String theatreName) {

	        Map<String, Object> model = new HashMap<>();
	        model.put("name", name);
	        model.put("theatreName", theatreName);

	        emailUtil.sendEmail(
	                to,
	                "Your Theatre Request is Approved",
	                "theatre_approval_email",   // <--- template file
	                model
	        );
	    }

	    // THEATRE REJECTION EMAIL
	    public void sendTheatreRejectionEmail(String to, String name, String theatreName, String reason) {

	        Map<String, Object> model = new HashMap<>();
	        model.put("name", name);
	        model.put("theatreName", theatreName);
	        model.put("reason", reason);

	        emailUtil.sendEmail(
	                to,
	                "Your Theatre Request is Rejected",
	                "theatre_rejection_email",  // <--- template file
	                model
	        );
	    }
	    
	    public void sendOtpMail(String to, String name, String otp) {
	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper =
	                    new MimeMessageHelper(message, true, "UTF-8");

	            helper.setTo(to);
	            helper.setSubject("Password Reset OTP");

	            Context context = new Context();
	            context.setVariable("name", name);
	            context.setVariable("otp", otp);

	            String html = templateEngine.process("otp-email", context);
	            helper.setText(html, true);

	            mailSender.send(message);

	        } catch (Exception e) {
	            throw new RuntimeException("OTP email sending failed");
	        }
	    }
	    
	    
}
