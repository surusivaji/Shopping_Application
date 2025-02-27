package com.siva.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Boolean existsByName(String name);
	
	List<Category> findByIsActiveTrue();

}
