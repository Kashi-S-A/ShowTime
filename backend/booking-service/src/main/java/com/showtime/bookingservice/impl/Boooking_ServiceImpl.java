package com.showtime.bookingservice.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.showtime.bookingservice.dto.BookingRequestDTO;
import com.showtime.bookingservice.dto.BookingResponseDTO;
import com.showtime.bookingservice.enums.BookingStatus;
import com.showtime.bookingservice.enums.StatusSeat;
import com.showtime.bookingservice.exception.BadRequestException;
import com.showtime.bookingservice.exception.ResourceNotFoundException;
import com.showtime.bookingservice.model.Booking;
import com.showtime.bookingservice.model.BookingSeat;
import com.showtime.bookingservice.model.Seat;
import com.showtime.bookingservice.model.SeatStatus;
import com.showtime.bookingservice.model.Show;
import com.showtime.bookingservice.model.User;
import com.showtime.bookingservice.repo.BookingRepo;
import com.showtime.bookingservice.repo.BookingSeatRepo;
import com.showtime.bookingservice.repo.SeatRepo;
import com.showtime.bookingservice.repo.SeatStatusRepo;
import com.showtime.bookingservice.repo.ShowRepo;
import com.showtime.bookingservice.repo.UserRepo;
import com.showtime.bookingservice.service.Booking_Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Boooking_ServiceImpl implements Booking_Service {

    private final UserRepo userRepository;
    private final ShowRepo showRepository;
    private final SeatRepo seatRepository;
    private final BookingRepo bookingRepository;
    private final BookingSeatRepo bookingSeatRepository;
    private final SeatStatusRepo seatStatusRepository;

    // ================= CREATE BOOKING =================
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO request) {

        // 1️ -> Validate request
	        if (request.getUser_id() == null)
	            throw new BadRequestException("User ID cannot be null");
	
	        if (request.getShow_id() == null)
	            throw new BadRequestException("Show ID cannot be null");
	
	        if (request.getSeat_id() == null || request.getSeat_id().isEmpty())
	            throw new BadRequestException("Seat IDs cannot be empty");

        // 2️ -> Validate user & show
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Show show = showRepository.findById(request.getShow_id())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        // 3️-> Check availability + release expired locks
        List<Seat> seats = checkSeatAvailability(request.getSeat_id(), show);

        // 4️ -> Calculate total amount
        double totalAmount = 0;
        for (Seat seat : seats) {
            totalAmount += seat.getCategory().getPrice();
        }

        // 5️-> Create booking (INITIATED)
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setTotal_amount(totalAmount);
        booking.setStatus(BookingStatus.INITIATED);
        booking.setCreated_at(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        // 6️-> Lock seats for 5 minutes (SeatStatus)
        List<SeatStatus> lockedSeatStatuses = new ArrayList<>();

        for (Seat seat : seats) {

        	SeatStatus seatStatus = seatStatusRepository.findBySeatAndShow(seat, show)
                    .orElse(new SeatStatus());
            seatStatus.setSeat(seat);
            seatStatus.setShow(show);
            seatStatus.setStatus("LOCKED");
            seatStatus.setLocked_until(LocalDateTime.now().plusMinutes(5));
            seatStatus.setLockedByUser(user);
            seatStatusRepository.save(seatStatus);
            lockedSeatStatuses.add(seatStatus);

            // Update Seat table as LOCKED
            seat.setStatus(StatusSeat.LOCKED);
            seatRepository.save(seat);
        }

        // 7️ -> Create BookingSeat records
        for (SeatStatus seatStatus : lockedSeatStatuses) {

            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setBooking(booking);
            bookingSeat.setStatus(seatStatus);
            bookingSeat.setAmount(
                    seatStatus.getSeat().getCategory().getPrice()
            );

            bookingSeatRepository.save(bookingSeat);
        }

        // 8️ -> Prepare response
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(booking.getBooking_id());
        response.setStatus(booking.getStatus());
        response.setAmount(totalAmount);
        response.setOrderId("BOOK-" + booking.getBooking_id());

        return response;
    }

    // ================= SEAT AVAILABILITY =================
    private List<Seat> checkSeatAvailability(List<Integer> seatIds, Show show) {

        List<Seat> seats = seatRepository.findBySeatIdIn(seatIds);

        if (seats.size() != seatIds.size()) {
            throw new ResourceNotFoundException("Invalid seat selection");
        }

        for (Seat seat : seats) {

            // Seat already booked
            if (seat.getStatus() == StatusSeat.BOOKED) {
                throw new BadRequestException(
                        "Seat " + seat.getSeatNumber() + " already booked"
                );
            }

            // Check SeatStatus lock
            SeatStatus seatStatus =
                    seatStatusRepository.findBySeatAndShow(seat, show).orElse(null);

            if (seatStatus != null && "LOCKED".equals(seatStatus.getStatus())) {

                //  Lock still valid
                if (seatStatus.getLocked_until().isAfter(LocalDateTime.now())) {
                    throw new BadRequestException(
                            "Seat " + seat.getSeatNumber() + " is temporarily locked"
                    );
                }

                //  Lock expired → release
                seatStatus.setStatus("AVAILABLE");
                seatStatus.setLocked_until(null);
                seatStatus.setLockedByUser(null);
                seatStatusRepository.save(seatStatus);
            }
        }
        return seats;
    }

    // ================= GET BOOKING BY ID =================
    @Override
    public BookingResponseDTO getBookingById(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        List<BookingSeat> bookingSeats =
                bookingSeatRepository.findByBooking(booking);

        double totalAmount = 0;
        for (BookingSeat bookingSeat : bookingSeats) {
            totalAmount += bookingSeat.getAmount();
        }
        
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(booking.getBooking_id());
        response.setStatus(booking.getStatus());
        response.setAmount(totalAmount);
        response.setOrderId("BOOK-" + booking.getBooking_id());

        return response;
    }

    // ================= GET ALL BOOKINGS =================
    @Override
    public List<BookingResponseDTO> getAllBookings() {

        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDTO> responses = new ArrayList<>();

        for (Booking booking : bookings) {

        	double totalAmount = 0;

        	// Fetch the list of BookingSeat objects for the booking
        	List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking(booking);

        	// Loop through each BookingSeat and add its amount to totalAmount
        	for (BookingSeat bs : bookingSeats) {
        	    totalAmount += bs.getAmount();
        	}
        	
            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setBookingId(booking.getBooking_id());
            dto.setStatus(booking.getStatus());
            dto.setAmount(totalAmount);
            dto.setOrderId("BOOK-" + booking.getBooking_id());

            responses.add(dto);
        }
        return responses;
    }

    // ================= GET BOOKINGS BY USER =================
    @Override
    public List<BookingResponseDTO> getBookingsByUserId(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Booking> bookings = bookingRepository.findByUser(user);
        List<BookingResponseDTO> responses = new ArrayList<>();

        for (Booking booking : bookings) {

        	double totalAmount = 0;
        	List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking(booking);
        	for (BookingSeat bs : bookingSeats) {
        	    totalAmount += bs.getAmount();
        	}

            BookingResponseDTO dto = new BookingResponseDTO();
            dto.setBookingId(booking.getBooking_id());
            dto.setStatus(booking.getStatus());
            dto.setAmount(totalAmount);
            dto.setOrderId("BOOK-" + booking.getBooking_id());

            responses.add(dto);
        }
        return responses;
    }

    // ================= CANCEL BOOKING =================
    @Override
    @Transactional
    public void cancelBooking(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            throw new BadRequestException("Only confirmed bookings can be cancelled");
        }

        List<BookingSeat> bookingSeats =
                bookingSeatRepository.findByBooking(booking);

        for (BookingSeat bs : bookingSeats) {

            SeatStatus seatStatus = bs.getStatus();
            seatStatus.setStatus("AVAILABLE");
            seatStatus.setLocked_until(null);
            seatStatus.setLockedByUser(null);
            seatStatusRepository.save(seatStatus);

            Seat seat = seatStatus.getSeat();
            seat.setStatus(StatusSeat.AVAILABLE);
            seatRepository.save(seat);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
