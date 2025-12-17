package com.showtime.theatre_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class TheatreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheatreServiceApplication.class, args);
	}

}
