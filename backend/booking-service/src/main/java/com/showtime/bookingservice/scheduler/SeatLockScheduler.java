package com.showtime.bookingservice.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.showtime.bookingservice.enums.StatusSeat;
import com.showtime.bookingservice.model.Seat;
import com.showtime.bookingservice.model.SeatStatus;
import com.showtime.bookingservice.repo.SeatRepo;
import com.showtime.bookingservice.repo.SeatStatusRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeatLockScheduler {

    private final SeatStatusRepo seatStatusRepository;
    private final SeatRepo seatRepository;

    // Runs every 1 minute
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void unlockExpiredSeats() {

        List<SeatStatus> expiredLocks =
                seatStatusRepository.findExpiredLockedSeats(LocalDateTime.now());

        for (SeatStatus seatStatus : expiredLocks) {

            //  Unlock SeatStatus
            seatStatus.setStatus("AVAILABLE");
            seatStatus.setLocked_until(null);
            seatStatus.setLockedByUser(null);

            //  Update Seat entity
            Seat seat = seatStatus.getSeat();
            seat.setStatus(StatusSeat.AVAILABLE);
        }
        // JPA will auto-flush at transaction commit
    }
}
