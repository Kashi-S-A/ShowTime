package com.showtime.bookingservice.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.bookingservice.model.BookingSeat;
import com.showtime.bookingservice.model.Show;

@Repository
public interface ShowRepo extends JpaRepository<Show, Integer>{
//	List<Show> findByMovieMovieId(Integer movieId);

//	List<Show> findByShowDate(LocalDate showDate);
}
