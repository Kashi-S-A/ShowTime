package com.showtime.theatre_service.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.theatre_service.entity.TheatreAdmin;
import com.showtime.theatre_service.entity.TheatreAdminReqStatus;

public interface TheatreAdminRepo extends JpaRepository<TheatreAdmin, Integer>{
	
	boolean existsByEmail(String email);
    Optional<TheatreAdmin> findByEmail(String email);
    Page<TheatreAdmin> findByStatus(TheatreAdminReqStatus status, Pageable pageable);
    

}
