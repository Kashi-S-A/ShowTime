package com.showtime.show_service.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.show_service.dto.ShowDTO;
import com.showtime.show_service.entity.Category;
import com.showtime.show_service.entity.Movie;
import com.showtime.show_service.entity.Screen;
import com.showtime.show_service.entity.Show;
import com.showtime.show_service.entity.ShowCategory;
import com.showtime.show_service.repository.CategoryRepository;
import com.showtime.show_service.repository.MovieRepository;
import com.showtime.show_service.repository.ScreenRepository;
import com.showtime.show_service.repository.ShowCategoryRepository;
import com.showtime.show_service.repository.ShowRepository;
import com.showtime.show_service.service.MovieService;
import com.showtime.show_service.service.ScreenService;
import com.showtime.show_service.service.ShowCategoryService;
import com.showtime.show_service.service.ShowService;

import lombok.Data;

@Data
@RestController
@RequestMapping("/show")
public class ShowController {
		
		@Autowired
		private MovieRepository movieRepository;
				
		@Autowired
		private ShowRepository showRepository;
		
		@Autowired
		private ShowService showService;
		
		@Autowired
		private CategoryRepository categoryRepository;
		
		@Autowired
		private ScreenRepository screenRepository;
						
		@Autowired
		private ShowCategoryService showCategoryService;
		
		@GetMapping("/add")
		public ShowDTO addShow(@RequestParam Long screen_id) 
		{			
			Screen screen = screenRepository.findByScreenId(screen_id);
			
			List<Category> category = screen.getCategory();
						
			ShowDTO dto = new ShowDTO();
			dto.setScreen_id(screen_id);
			dto.setCategory(category);
			
			return dto;
		}
		
		
		//Add Show to the theatre
		@PostMapping("/add")
		public ResponseEntity<String> addShow(@RequestBody ShowDTO dto) 
		{			
			Show show = new Show();
			show.setShowDate(dto.getShow_date());
			show.setShow_starttime(dto.getShow_starttime());
			show.setShow_endtime(dto.getShow_endtime());
			show.setLanguage(dto.getLanguages());
			
			String title = dto.getTitle();
			Movie movie = movieRepository.findByTitle(title);	
									
			Long screen_id = dto.getScreen_id();
			Screen screen = screenRepository.findByScreenId(screen_id);
			
			if(movie!=null && screen!=null)	{
				show.setMovie(movie);
				show.setScreen(screen);
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie or Screen Not found");
			}
	
			List<Category> categoryDTO =  dto.getCategory();
		    
			List<ShowCategory> savedCategory = showCategoryService.saveCategory(categoryDTO);
			
			show.setShowCategory(savedCategory);
							
			return showService.addShow(show);
		}
		
		
		//View particular Show
		@GetMapping("/view")
		public Show getShow(Integer show_id)
		{
				Show show = showRepository.findByShowId(show_id);
								
				return show;
		}
				
		
		//View List of Shows for the day on a particular screen
		@GetMapping("/viewshows")
		public List<Show> getShowByDate(@RequestParam LocalDate show_date, @RequestParam Long screen_id)
		{
			Screen screen = screenRepository.findByScreenId(screen_id);

			List<Show> shows= null;
				if(screen!=null)
					shows = showService.findByShowDate(show_date, screen_id);
				
			return shows;
		}
		
		
		//View List of Shows for the particular day 
		@GetMapping("/allshows")
		public List<Show> getAllShow(@RequestParam LocalDate show_date)
		{
				List<Show> shows = showService.findByShowDate(show_date);
						
				return shows;
		}
		
		
		//Delete a particular show
		@DeleteMapping("/delete")
		public ResponseEntity<String> deleteShow(@RequestParam Integer show_id)
		{
			return showService.deleteByShowId(show_id);
		}
		
		
		//Delete all shows for the day
		@DeleteMapping("/deleteAll")
		public ResponseEntity<String> deleteShow(@RequestParam LocalDate show_date)
		{
			return showService.deleteByShowDate(show_date);
		}
		
		
		@GetMapping("/edit")
		public Show fetchShow(@RequestParam Integer show_id)
		{
				Show show = showRepository.findByShowId(show_id);				
				return show;
		}
		
		
		//edit a particular show
		@PutMapping("/edit")
		public ResponseEntity<String> editShow(@RequestBody ShowDTO dto, @RequestParam Integer show_id)
		{
			Show show = showRepository.findByShowId(show_id);
			
			if(show!=null)	{
				show.setShowDate(dto.getShow_date());
				show.setShow_starttime(dto.getShow_starttime());
				show.setShow_endtime(dto.getShow_endtime());
				show.setLanguage(dto.getLanguages());
			}	
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show Not Found");
			}	
			
			String title = dto.getTitle();
			Movie movie = movieRepository.findByTitle(title);	
									
			Long screen_id = dto.getScreen_id();
			Screen screen = screenRepository.findByScreenId(screen_id);
			
			if(movie!=null && screen!=null)	{
				show.setMovie(movie);
				show.setScreen(screen);
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie or Screen Not found");
			}
	
			List<Category> categoryDTO =  dto.getCategory();
		    
			List<ShowCategory> updatedCategory = showCategoryService.updateCategory(categoryDTO);
			
			show.setShowCategory(updatedCategory);
							
			return showService.updateShow(show);
		}
		

}

