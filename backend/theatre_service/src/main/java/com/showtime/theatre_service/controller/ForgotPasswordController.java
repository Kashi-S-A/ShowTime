package com.showtime.theatre_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.showtime.theatre_service.service.ForgotPasswordService;

@RestController
@RequestMapping("/theatreAdmin/password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        return ResponseEntity.ok(forgotPasswordService.sendOtp(email));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {
    	 try {
    	        return ResponseEntity.ok(
    	                forgotPasswordService.verifyOtp(email, otp)
    	        );
    	    } catch (RuntimeException ex) {
    	        return ResponseEntity
    	                .badRequest()
    	                .body(Map.of(
    	                        "error", ex.getMessage(),
    	                        "status", "FAILED"
    	                ));
    	    }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        return ResponseEntity.ok(
                forgotPasswordService.resetPassword(
                        email, newPassword, confirmPassword));
    }
}
