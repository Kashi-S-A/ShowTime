package com.showtime.screen_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showtime.screen_service.dto.SeatCellDTO;
import com.showtime.screen_service.dto.SeatLayoutDTO;
import com.showtime.screen_service.dto.SeatRowDTO;
import com.showtime.screen_service.entity.Category;
import com.showtime.screen_service.entity.Screen;
import com.showtime.screen_service.entity.Seat;
import com.showtime.screen_service.entity.SeatLayout;
import com.showtime.screen_service.entity.Theatre_Status;
import com.showtime.screen_service.repository.CategoryRepo;
import com.showtime.screen_service.repository.ScreenRepo;
import com.showtime.screen_service.repository.SeatRepo;
import com.showtime.screen_service.repository.seatLayoutRepo;

import jakarta.transaction.Transactional;

@Service
public class SeatService {
	
	@Autowired
	seatLayoutRepo seatLayoutRepo;
	
	@Autowired
	ScreenRepo screenRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	SeatRepo seatRepo;
	
	@Transactional
	public void createSeatLayout(SeatLayoutDTO dto) {

	    // 1. Save layout
	    SeatLayout seatLayout = new SeatLayout();
	    seatLayout.setScreenId(dto.getScreenId());
	    seatLayout.setRowLabel(dto.getRowLabel().toUpperCase());
	    List<Integer> layoutList = dto.getLayout();
	    StringBuilder layoutBuilder = new StringBuilder();
	    for (int i = 0; i < layoutList.size(); i++) {
	        layoutBuilder.append(layoutList.get(i));
	        if (i < layoutList.size() - 1) {
	            layoutBuilder.append(",");
	        }
	    }

	    seatLayout.setLayout(layoutBuilder.toString());
	    seatLayoutRepo.save(seatLayout);

	    // 2. Fetch screen & category
	    Screen screen = screenRepo.findById(dto.getScreenId())
	            .orElseThrow(() -> new RuntimeException("Screen not found"));

	    Category category = categoryRepo.findById(dto.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found"));

	    // 3. Create seats
	    for (Integer value : dto.getLayout()) {
	        if (value != 0) {
	            Seat seat = new Seat();
	            seat.setSeatNumber(value);
	            seat.setRowLabel(dto.getRowLabel().toUpperCase());
	            seat.setScreen(screen);
	            seat.setCategory(category);
	            seat.setStatus(Theatre_Status.AVAILABLE);
	            seatRepo.save(seat);
	        }
	    }
	}
	
	public List<SeatRowDTO> getSeatLayout(Integer screenId) {

	    List<SeatLayout> layouts = seatLayoutRepo.findByScreenId(screenId);
	    List<Seat> seats = seatRepo.findByScreen_ScreenId(screenId);

	    Map<String, Map<Integer, Seat>> seatMap =
	        seats.stream().collect(
	            Collectors.groupingBy(
	                Seat::getRowLabel,
	                Collectors.toMap(Seat::getSeatNumber, s -> s)
	            )
	        );

	    List<SeatRowDTO> response = new ArrayList<>();

	    for (SeatLayout layout : layouts) {

	        SeatRowDTO rowDTO = new SeatRowDTO();
	        rowDTO.setRowLabel(layout.getRowLabel());

	        List<SeatCellDTO> cells = new ArrayList<>();
	        String[] values = layout.getLayout().split(",");

	        for (String v : values) {
	            SeatCellDTO cell = new SeatCellDTO();
	            int num = Integer.parseInt(v);

	            if (num == 0) {
	                cell.setType("GAP");
	            } else {
	                Seat seat = seatMap .get(layout.getRowLabel()).get(num);
	                cell.setType("SEAT");
	                cell.setSeatNumber(num);
	                cell.setStatus(seat.getStatus());
	            }
	            cells.add(cell);
	        }

	        rowDTO.setCells(cells);
	        response.add(rowDTO);
	    }

	    return response;
	}

}
