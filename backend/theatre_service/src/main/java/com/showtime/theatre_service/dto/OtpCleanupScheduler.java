package com.showtime.theatre_service.dto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.showtime.theatre_service.repository.ForgotPasswordRepo;

import jakarta.transaction.Transactional;

@Component
public class OtpCleanupScheduler {

    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;
    @Transactional 
    @Scheduled(fixedRate = 60000) // every 1 min
    public void deleteExpiredOtps() {
        forgotPasswordRepo.deleteByExpiryTimeBefore(LocalDateTime.now());
    }
}
