package com.showtime.bookingservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.showtime.bookingservice.model.SeatStatus;
import com.showtime.bookingservice.model.User;

import jakarta.persistence.LockModeType;

public interface SeatStatusRepo extends JpaRepository<SeatStatus, Integer> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SeatStatus s WHERE s.show.id = :showId AND s.seat.seatId IN :seatIds")
    List<SeatStatus> findLockedSeats(@Param("showId") Integer showId,
                                     @Param("seatIds") List<Integer> seatIds);

	 @Query("SELECT s FROM SeatStatus s WHERE s.seat.seatId = :seatId AND s.show.show_id = :showId")
	    Optional<SeatStatus> findBySeatAndShow(
	            @Param("seatId") Integer seatId,
	            @Param("showId") Integer showId
	    );

	
	 
	
}
