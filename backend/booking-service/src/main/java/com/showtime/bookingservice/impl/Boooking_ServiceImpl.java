package com.showtime.bookingservice.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class Boooking_ServiceImpl implements Booking_Service{
	
	private final UserRepo userRepository;
    private final ShowRepo showRepository;
    private final SeatRepo seatRepository;
    private final BookingRepo bookingRepository;
    private final BookingSeatRepo bookingSeatRepository;
    private final SeatStatusRepo seatStatusRepository;
    
    
    // 1. To Create booking
	@Override
	@Transactional
	public BookingResponseDTO createBooking(BookingRequestDTO request) {
		
		 // Validate request IDs
		    if (request.getUser_id() == null) {
		        throw new BadRequestException("User ID cannot be null");
		    }
		    if (request.getShow_id() == null) {
		        throw new BadRequestException("Show ID cannot be null");
		    }
		    if (request.getSeat_id() == null || request.getSeat_id().isEmpty() || request.getSeat_id().contains(null)) {
		        throw new BadRequestException("Seat IDs cannot be null or empty");
		    }

        // 1. Validate user and show
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        Show show = showRepository.findById(request.getShow_id())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        // Check seat Availability
      List<Seat> seats = checkSeatAvailability(request.getSeat_id());
        
        
        // 4. Lock the seats temporarily
        for (Seat seat : seats) {
            seat.setStatus(StatusSeat.LOCKED);
            seatRepository.save(seat);
        }
               
        // 5. Calculate amount
        double totalAmount = 0;

        for (Seat seat : seats) {
            totalAmount += seat.getCategory().getPrice();
        }
        
        // 6. Create booking with INITIATED status
   
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setTotal_amount(totalAmount);
        booking.setStatus(BookingStatus.INITIATED);
        booking.setCreated_at(LocalDateTime.now());
        
        booking = bookingRepository.save(booking);
        
     // 7. Locking seats before creating BookingSeat
        List<SeatStatus> lockedSeatStatuses = new ArrayList<>(); // collect locked SeatStatus

        for (Seat seat : seats) {
            SeatStatus seatStatus = new SeatStatus();
            seatStatus.setSeat(seat);
            seatStatus.setShow(show);
            seatStatus.setStatus("LOCKED");
            seatStatus.setLocked_until(LocalDateTime.now().plusMinutes(5)); // 5 min lock
            seatStatus.setLockedByUser(user);

            // Save SeatStatus
            seatStatusRepository.save(seatStatus);

            // Add to list for later
            lockedSeatStatuses.add(seatStatus);
        }

        // 8. Create BookingSeat records
        for (SeatStatus seatStatus : lockedSeatStatuses) {
            BookingSeat bookingSeat = new BookingSeat();

            bookingSeat.setBooking(booking);                                     // link booking
            bookingSeat.setStatus(seatStatus);                                    // link SeatStatus
            bookingSeat.setAmount(seatStatus.getSeat().getCategory().getPrice()); // get seat price

            bookingSeatRepository.save(bookingSeat);                              // save to DB
        }


     // 9. Prepare and return response
        BookingResponseDTO response = new BookingResponseDTO();
        String orderId = "BOOK-" + booking.getBooking_id();

        response.setBookingId(booking.getBooking_id());
        response.setStatus(booking.getStatus());
        response.setAmount(totalAmount);
        response.setOrderId(orderId);

        return response;
	}
	
	public List<Seat> checkSeatAvailability(List<Integer> seatIds) {
		 // 2. Lock seats (pessimistic)
        List<Seat> seats = seatRepository.findBySeatIdIn(seatIds);
        

        if (seats.size() != seatIds.size()) {
            throw new ResourceNotFoundException("One or more seats are invalid");
        }
        
        // 3. Check availability
        for (Seat seat : seats) {
        	 StatusSeat status = seat.getStatus();

        	    if (status == StatusSeat.BOOKED || status == StatusSeat.LOCKED) {
        	        throw new BadRequestException("Seat " + seat.getSeatNumber() + " is locked");
        	    }
        }
        return seats;
	}	
	
	//2. To find booking by booking Id

	@Override
	public BookingResponseDTO getBookingById(Integer bookingId) {
		 Booking booking = bookingRepository.findById(bookingId)
	                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

	    // Sum amount from BookingSeat if you want accuracy
		 List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking(booking);
		 double totalAmount = 0.0;
		 for (BookingSeat bs : bookingSeats) {
		     totalAmount += bs.getAmount();
		 }

	        BookingResponseDTO response = new BookingResponseDTO();
	        response.setBookingId(booking.getBooking_id());
//	        response.setStatus("BOOKED"); // or actual status if you have it
	        response.setStatus(booking.getStatus());
	        response.setAmount(totalAmount);
	        response.setOrderId("BOOK-" + booking.getBooking_id());

	        return response;
	}	
	
	//3. To get all bookings
	@Override
	public List<BookingResponseDTO> getAllBookings() {
	    List<Booking> bookings = bookingRepository.findAll();
	    List<BookingResponseDTO> responseList = new ArrayList<>();

	    for (Booking booking : bookings) {
	        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking(booking);
	        double totalAmount = 0.0;
	        for (BookingSeat bs : bookingSeats) {
	            totalAmount += bs.getAmount();
	        }

	        BookingResponseDTO dto = new BookingResponseDTO();
	        dto.setBookingId(booking.getBooking_id());
	        dto.setStatus(booking.getStatus()); // Or use actual booking status
	        dto.setAmount(totalAmount);
	        dto.setOrderId("BOOK-" + booking.getBooking_id());

	        responseList.add(dto);
	    }
	    return responseList;
	
	} 
	
	// 4. get booking by user_id
	@Override
	public List<BookingResponseDTO> getBookingsByUserId(Integer userId) {
		
		User user = userRepository.findById(userId)
		            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		 
		List<Booking> bookings = bookingRepository.findByUser(user);
		
		 List<BookingResponseDTO> responseList = new ArrayList<>();
		    for (Booking booking : bookings) {
		        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking(booking);
		        double totalAmount = 0.0;
		        for (BookingSeat bs : bookingSeats) {
		            totalAmount += bs.getAmount();
		        }

		        BookingResponseDTO dto = new BookingResponseDTO();
		        dto.setBookingId(booking.getBooking_id());
		        dto.setStatus(booking.getStatus());
		        dto.setAmount(totalAmount);
		        dto.setOrderId("BOOK-" + booking.getBooking_id());

		        responseList.add(dto);
		    }
		    return responseList;
		}
		
	
	// 5. cancel booking
	
	@Override
	@Transactional
	public void cancelBooking(Integer bookingId) {
	    
	    Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

	    if (!booking.getStatus().equals(BookingStatus.CONFIRMED)) {
	        throw new BadRequestException("You can cancel only confirmed bookings");
	    }

	 // 2️- Unlock seats associated with this booking
	    List<BookingSeat> bookingSeats = bookingSeatRepository.findByBooking(booking);
	    for (BookingSeat bs : bookingSeats) {
	        SeatStatus seatStatus = bs.getStatus();
	        seatStatus.setStatus("AVAILABLE"); // unlock
	        seatStatus.setLockedByUser(null);
	        seatStatus.setLocked_until(null);
	        seatStatusRepository.save(seatStatus);

	        // Update Seat entity as well
	        Seat seat = seatStatus.getSeat();
	        seat.setStatus(StatusSeat.AVAILABLE);
	        seatRepository.save(seat);
	    }

	    // 3️- Update booking status to CANCELLED
	    booking.setStatus(BookingStatus.CANCELLED);
	    bookingRepository.save(booking);
	}
}
