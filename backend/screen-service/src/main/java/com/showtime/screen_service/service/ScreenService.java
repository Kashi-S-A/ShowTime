package com.showtime.screen_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showtime.screen_service.dto.ScreenDTO;
import com.showtime.screen_service.dto.ScreenResponseDTO;
import com.showtime.screen_service.entity.Category;
import com.showtime.screen_service.entity.Screen;
import com.showtime.screen_service.entity.Theatre;
import com.showtime.screen_service.repository.CategoryRepo;
import com.showtime.screen_service.repository.ScreenRepo;
import com.showtime.screen_service.repository.TheatreRepo;

@Service
public class ScreenService {
	@Autowired
    private ScreenRepo screenRepo;

    @Autowired
    private TheatreRepo theatreRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public void createScreen(ScreenDTO dto) {

    	Theatre theatre = theatreRepo.findById(dto.getTheater_id()).orElseThrow();

        Screen screen = new Screen();
        screen.setScreenName(dto.getScreenName());
        screen.setFormatSupported(dto.getFormat_supported());
        screen.setTheatre(theatre);

        Screen savedScreen = screenRepo.save(screen);

        // 3️⃣ Create Categories for this Screen
        if (dto.getCategory() != null) {

            for (Category cat : dto.getCategory()) {
                Category category = new Category();
                category.setCategory_name(cat.getCategory_name());
                category.setScreen(savedScreen);

                categoryRepo.save(category);
            }
        }
    }

	public List<ScreenResponseDTO> getScreensByTheatre(Integer theatreId) {
		List<Screen> screens =
		        screenRepo.findByTheatre_TheatreId(theatreId);

		List<ScreenResponseDTO> response = new ArrayList<>();

		for (Screen screen : screens) {
		    ScreenResponseDTO dto = new ScreenResponseDTO();
		    dto.setScreenId(screen.getScreenId());
		    dto.setScreenName(screen.getScreenName());
		    dto.setFormatSupported(screen.getFormatSupported());
		    response.add(dto);
		}

		return response;
	}
}
