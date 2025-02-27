package com.siva.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByIsActiveTrue();
	
	List<Product> findByCategory(String category);
	
	List<Product> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch1);
}
