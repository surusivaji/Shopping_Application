package com.siva.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.Cart;
import com.siva.shopping.model.Product;
import com.siva.shopping.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	Cart findByUserAndProduct(User user, Product product);
	
	Integer countCartByUser(User user);
	
	List<Cart> findByUser(User user);

}
