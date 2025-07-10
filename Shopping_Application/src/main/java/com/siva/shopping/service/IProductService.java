package com.siva.shopping.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.siva.shopping.model.Product;

public interface IProductService {
	
	Product saveProduct(Product product);
	
	List<Product> getAllProducts();
	
	Page<Product> getAllProducts(int PageNo);
	
	Product getProductById(int id);
	
	boolean deleteProduct(Product product);
	
	List<Product> activeProducts();
	
	Page<Product> getProductsByCategory(String categroy, int pageNo);
	
	List<Product> searchProducts(String ch);
	
	Page<Product> getActiveProducts(int pageNo);

	 List<Product> getProductsByCategory(String category);
	 
}
