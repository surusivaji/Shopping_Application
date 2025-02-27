package com.siva.shopping.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.siva.shopping.model.Category;

public interface ICategoryService {
	
	Category saveCategory(Category category);
	
	List<Category> allCategories();
	
	Page<Category> getAllCategories(int pageNo);
	
	boolean isCategoryExists(String name);
	
	Category getCategoryById(int id);
	
	boolean deleteCategory(Category category);
	
	List<Category> activeCategories();

}
