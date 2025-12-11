package com.showtime.show_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.show_service.entity.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

}
