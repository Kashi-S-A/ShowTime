package com.showtime.show_service.service;

import java.util.List;

import com.showtime.show_service.entity.Category;
import com.showtime.show_service.entity.ShowCategory;

public interface ShowCategoryService {

	List<ShowCategory> saveCategory(List<Category> categoryDTO);

	List<ShowCategory> updateCategory(List<Category> categoryDTO);

}
