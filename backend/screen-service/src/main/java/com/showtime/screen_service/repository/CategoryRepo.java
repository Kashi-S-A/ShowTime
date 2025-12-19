package com.showtime.screen_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.screen_service.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
