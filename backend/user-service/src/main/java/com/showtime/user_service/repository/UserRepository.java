package com.showtime.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
		boolean existsByEmail(String email);
		boolean existsByPhone(String phone);
		User findByEmail(String email);
}
