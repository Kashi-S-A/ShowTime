package com.showtime.show_service.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.DtoInstantiatingConverter;
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
import com.showtime.show_service.repository.CategoryRepository;
import com.showtime.show_service.repository.MovieRepository;
import com.showtime.show_service.repository.ScreenRepository;
import com.showtime.show_service.repository.ShowRepository;
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
				
		@GetMapping("/add")
		public ShowDTO addShow()
		{
			ShowDTO dto = new ShowDTO();
			return dto;
		}
		
		
		//Adding Shows in the theatre
		@PostMapping("/add")
		public ResponseEntity<String> addShow(@RequestBody ShowDTO dto) 
		{			
			String title = dto.getTitle();
			Movie movie = movieRepository.findByTitle(title);	
									
			Long screen_id = dto.getScreen_id();
			Screen screen = screenRepository.findByScreenId(screen_id);
			
			Show show = new Show();
			show.setShowDate(dto.getShow_date());
			show.setShow_time(dto.getShow_time());
			show.setLanguage(dto.getLanguages());
			
			if(movie!=null && screen!=null)
				show.setMovie(movie);
				show.setScreen(screen);
			
			//fetch from Screen Layout
			List<Category> fetchedcategory = screen.getCategory();
	
			//Categories Added By Theatre Admin	
			List<Category> categorydto =  dto.getCategory();
		    
			if(categorydto!=null)
			{
				for(Category catDTO: categorydto)
				{
					for(Category fetchCat: fetchedcategory)
					{
						if(catDTO.getCategory_name().equals(fetchCat.getCategory_name()))
							fetchCat.setPrice(catDTO.getPrice());
					}
				}
			}
			
			//returning the fetched category by setting price to that category
			screen.setCategory(fetchedcategory);
			show.setScreen(screen);
			
			List<Category> savedCategoryPrice = categoryRepository.saveAll(fetchedcategory);
							
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
		public ShowDTO fetchShow(@RequestParam Integer show_id)
		{
				Show show = showRepository.findByShowId(show_id);

				ShowDTO dto = new ShowDTO();
				dto.setCategory(show.getScreen().getCategory());
				dto.setShow_date(show.getShowDate());
				dto.setShow_time(show.getShow_time());
				dto.setLanguages(show.getLanguage());
				dto.setTitle(show.getMovie().getTitle());
				dto.setScreen_id(show.getScreen().getScreenId());
				
				return dto;
		}
		
		//edit a particular show
		@PutMapping("/edit")
		public ResponseEntity<String> editShow(@RequestBody ShowDTO dto, @RequestParam Integer show_id)
		{
			
			Show show = showRepository.findByShowId(show_id);
			
			String title = dto.getTitle();
			Movie movie = movieRepository.findByTitle(title);	
									
			Long screen_id = dto.getScreen_id();
			Screen screen = screenRepository.findByScreenId(screen_id);
			
			show.setShowDate(dto.getShow_date());
			show.setShow_time(dto.getShow_time());
			show.setLanguage(dto.getLanguages());
			
			if(movie!=null && screen!=null)
				show.setMovie(movie);
				show.setScreen(screen);
			
			//fetch from Screen Layout
			List<Category> fetchedcategory = screen.getCategory();
	
			//Categories Added By Theatre Admin	
			List<Category> categorydto =  dto.getCategory();
		    
			if(categorydto!=null)
			{
				for(Category catDTO: categorydto)
				{
					for(Category fetchCat: fetchedcategory)
					{
						if(catDTO.getCategory_name().equals(fetchCat.getCategory_name()))
							fetchCat.setPrice(catDTO.getPrice());
					}
				}
			}
			
			//returning the fetched category by setting price to that category
			screen.setCategory(fetchedcategory);
			show.setScreen(screen);
			
			List<Category> savedCategoryPrice = categoryRepository.saveAll(fetchedcategory);
			
			return showService.updateShow(show);
		}
		

}

