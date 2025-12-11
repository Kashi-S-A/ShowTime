package com.showtime.show_service.service.Imp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.showtime.show_service.entity.Show;
import com.showtime.show_service.repository.ShowRepository;
import com.showtime.show_service.service.ShowService;

@Service
public class ShowServiceImp implements ShowService {

	@Autowired
	private ShowRepository showRepository;
	
	@Override
	public ResponseEntity<String> addShow(Show show) {

		Show savedShow = showRepository.save(show);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Show is saved with show_id : " + savedShow.getShowId());
	}
	
	
	@Override
	public List<Show> findByShowDate(LocalDate show_date) {

		List<Show> shows = showRepository.findByShowDate(show_date);

		return shows;
	}


	@Override
	public List<Show> findByShowDate(LocalDate show_date, Long screen_id) {

		//fetch list of shows by date
		List<Show> shows = showRepository.findByShowDate(show_date);
		
		List<Show> showperscreen = new ArrayList<>();
		
		//filtered list of shows by screen_id
		for(Show show: shows)
		{
				if(show.getScreen().getScreenId().equals(screen_id))
					showperscreen.add(show);
		}
		
		return showperscreen;
	}

	
	@Override
	public ResponseEntity<String> updateShow(Show show) {

		Show updateShow = showRepository.save(show);
		
		return ResponseEntity.status(HttpStatus.OK).body("Show is updated with show_id : " + updateShow.getShowId());
	}
	
	
	@Transactional
	@Override
	public ResponseEntity<String> deleteByShowId(Integer show_id) {
		
		showRepository.deleteByShowId(show_id);
		
		return ResponseEntity.status(HttpStatus.OK).body("Show is deleted with "+show_id);
	}
	
	

	@Transactional
	@Override
	public ResponseEntity<String> deleteByShowDate(LocalDate show_date) {

		showRepository.deleteByShowDate(show_date);
		
		return ResponseEntity.status(HttpStatus.OK).body("All Shows deleted dated on "+show_date);

	}

}
