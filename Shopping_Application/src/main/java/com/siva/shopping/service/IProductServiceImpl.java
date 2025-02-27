package com.siva.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.siva.shopping.model.Product;
import com.siva.shopping.repository.ProductRepository;

@Service
public class IProductServiceImpl implements IProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {
		try {
			Product save = productRepository.save(product);
			return save;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Product> getAllProducts() {
		try {
			List<Product> products = productRepository.findAll();
			return products;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Page<Product> getAllProducts(int PageNo) {
		try {
			Pageable pageable = PageRequest.of(PageNo, 6);
			Page<Product> page = productRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Product getProductById(int id) {
		try {
			Optional<Product> optional = productRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean deleteProduct(Product product) {
		try {
			productRepository.delete(product);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Product> activeProducts() {
		try {
			List<Product> products = productRepository.findByIsActiveTrue();
			return products;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Product> getProductsByCategory(String category) {
		try {
			if (ObjectUtils.isEmpty(category)) {
				return productRepository.findByIsActiveTrue();
			}
			else {
				return productRepository.findByCategory(category);
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Product> searchProducts(String ch) {
		try {
			List<Product> products = productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
			return products;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
