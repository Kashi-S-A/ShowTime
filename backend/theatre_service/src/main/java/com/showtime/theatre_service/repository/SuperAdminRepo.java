package com.showtime.theatre_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.theatre_service.entity.SuperAdmin;

public interface SuperAdminRepo extends JpaRepository<SuperAdmin, Integer>{
	Optional<SuperAdmin> findByEmail(String email);
}
