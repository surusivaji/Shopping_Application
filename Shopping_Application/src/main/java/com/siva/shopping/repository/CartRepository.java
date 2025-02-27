package com.siva.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.Cart;
import com.siva.shopping.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	Cart findByUserIdAndProductId(Integer userId, Integer productId);
	
	Integer countCartByUser(User user);
	
	List<Cart> findByUser(User user);

}
