package com.showtime.theatre_service.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.theatre_service.entity.ForgotPassword;
import com.showtime.theatre_service.entity.TheatreAdmin;


@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Integer> {
	Optional<ForgotPassword>
	findTopByTheatreAdminAndOtpAndUsedFalseOrderByExpiryTimeDesc(
	        TheatreAdmin admin, String otp);

	
	void deleteByExpiryTimeBefore(LocalDateTime time);


	void deleteByTheatreAdmin(TheatreAdmin theatreAdmin);
}
