package com.siva.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.siva.shopping.model.Category;
import com.siva.shopping.repository.CategoryRepository;

@Service
public class ICategoryServiceImpl implements ICategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category saveCategory(Category category) {
		try {
			Category save = categoryRepository.save(category);
			if (save!=null) {
				return save;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Category> allCategories() {
		try {
			List<Category> list = categoryRepository.findAll();
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Page<Category> getAllCategories(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 6);
			Page<Category> page = categoryRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean isCategoryExists(String name) {
		try {
			Boolean exists = categoryRepository.existsByName(name);
			return exists;
		} catch (Exception e) {
			return false;
		}
	} 
	
	@Override
	public Category getCategoryById(int id) {
		try {
			Optional<Category> optional = categoryRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean deleteCategory(Category category) {
		try {
			categoryRepository.delete(category);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Category> activeCategories() {
		try {
			List<Category> categories = categoryRepository.findByIsActiveTrue();
			return categories;
		} catch (Exception e) {
			return null;
		}
	}
	

}
