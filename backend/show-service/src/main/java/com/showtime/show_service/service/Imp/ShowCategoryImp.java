package com.showtime.show_service.service.Imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showtime.show_service.entity.Category;
import com.showtime.show_service.entity.ShowCategory;
import com.showtime.show_service.repository.ShowCategoryRepository;
import com.showtime.show_service.service.ShowCategoryService;

@Service
public class ShowCategoryImp implements ShowCategoryService  {

	@Autowired
	private ShowCategoryRepository showCategoryRepository;
	
	@Override
	public List<ShowCategory> saveCategory(List<Category> categoryDTO) {
				
		List<ShowCategory> showCategory = new ArrayList();
		
		for(Category category : categoryDTO)
		{
			ShowCategory sc = new ShowCategory();
			sc.setCategory_id(category.getCategory_id());
			sc.setCategory_name(category.getCategory_name());
			sc.setPrice(category.getPrice());
			
			showCategory.add(sc);
		}
		
		List<ShowCategory> savedCategory= showCategoryRepository.saveAll(showCategory);

		return savedCategory;
	}

	@Override
	public List<ShowCategory> updateCategory(List<Category> categoryDTO) {
		
		List<ShowCategory> showCategory = new ArrayList();
		
		for(Category category : categoryDTO)
		{
			ShowCategory sc = new ShowCategory();
			sc.setCategory_id(category.getCategory_id());
			sc.setCategory_name(category.getCategory_name());
			sc.setPrice(category.getPrice());
			
			showCategory.add(sc);
		}
		
		List<ShowCategory> updatedCategory= showCategoryRepository.saveAll(showCategory);
		
		return updatedCategory;
	}

}
