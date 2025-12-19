package com.showtime.screen_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.showtime.screen_service.entity.Theatre;


public interface TheatreRepo extends JpaRepository<Theatre, Integer>{

}
