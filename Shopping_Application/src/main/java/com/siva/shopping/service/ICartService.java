package com.siva.shopping.service;

import java.util.List;

import com.siva.shopping.model.Cart;
import com.siva.shopping.model.User;

public interface ICartService {
	
	Cart saveCart(Integer uid, Integer pid);
	
	List<Cart> getCartByUser(User user);
	
	Integer countCartByUser(User user);

	Boolean updateQuantity(String status, Integer cid);

}
