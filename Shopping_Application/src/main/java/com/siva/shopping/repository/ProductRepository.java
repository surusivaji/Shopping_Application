package com.siva.shopping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByIsActiveTrue();

	Page<Product> findByIsActiveTrue(Pageable pageable);
	
	Page<Product> findByCategoryAndIsActiveTrue(String category, Pageable pageable);
	
	List<Product> findByCategoryAndIsActiveTrue(String category);
	
	List<Product> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseAndIsActiveTrue(String ch, String ch1);
	
}
