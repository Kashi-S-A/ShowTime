package com.showtime.bookingservice.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.showtime.bookingservice.model.Seat;
import com.showtime.bookingservice.model.SeatStatus;
import com.showtime.bookingservice.model.Show;

public interface SeatStatusRepo extends JpaRepository<SeatStatus, Integer> {

    //  Find seat status for a seat + show
    @Query("""
        SELECT s FROM SeatStatus s
        WHERE s.seat = :seat
        AND s.show = :show
    """)
    Optional<SeatStatus> findBySeatAndShow(
            @Param("seat") Seat seat,
            @Param("show") Show show
    );

    //  Used by scheduler to unlock expired seats
    @Query("""
            SELECT s FROM SeatStatus s
            WHERE s.status = 'LOCKED'
            AND s.Locked_until < :now
        """)
        List<SeatStatus> findExpiredLockedSeats(
                @Param("now") LocalDateTime now
        );
}
