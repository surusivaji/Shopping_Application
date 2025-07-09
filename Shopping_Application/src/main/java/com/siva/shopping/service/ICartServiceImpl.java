package com.siva.shopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.siva.shopping.model.Cart;
import com.siva.shopping.model.Product;
import com.siva.shopping.model.User;
import com.siva.shopping.repository.CartRepository;
import com.siva.shopping.repository.ProductRepository;
import com.siva.shopping.repository.UserRepository;

@Service
public class ICartServiceImpl implements ICartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Cart saveCart(Integer uid, Integer pid) {
		try {
			User user = userRepository.findById(uid).get();
			Product product = productRepository.findById(pid).get();
			Cart cart = cartRepository.findByUserAndProduct(user, product);
			Cart save = null;
			if (ObjectUtils.isEmpty(cart)) {
				save = new Cart();
				save.setProduct(product);
				save.setUser(user);
				save.setQuantity(1);
				save.setTotalPrice(1*product.getDiscountPrice());
			}
			else {
				save = cart;
				save.setQuantity(cart.getQuantity()+1);
				save.setTotalPrice(cart.getQuantity()*cart.getProduct().getDiscountPrice());
			}
			Cart saved = cartRepository.save(save);
			if (saved!=null) {
				return saved;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Cart> getCartByUser(User user) {
		try {
			List<Cart> cart = cartRepository.findByUser(user);
			List<Cart> updatedCart = new ArrayList<Cart>();
			Double totalOrderPrice = 0.0;
			for (Cart c : cart) {
				Double totalPrice = c.getQuantity()*c.getProduct().getDiscountPrice();
				c.setTotalPrice(totalPrice);
				totalOrderPrice = totalPrice+totalOrderPrice;
				c.setTotalOrderPrice(totalOrderPrice);
				updatedCart.add(c);
			}
			return updatedCart;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Integer countCartByUser(User user) {
		try {
			Integer countCart = cartRepository.countCartByUser(user);
			return countCart;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean updateQuantity(String status, Integer cid) {
		try {
			Cart cart = cartRepository.findById(cid).get();
			int productQuantity;
			if (status.equalsIgnoreCase("decrease")) {
				productQuantity = cart.getQuantity()-1;
				if (productQuantity<=0) {
					cartRepository.delete(cart);
					return false;
				}
			}
			else {
				productQuantity = cart.getQuantity()+1;
			}
			cart.setQuantity(productQuantity);
			Cart save = cartRepository.save(cart);
			if (save!=null) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
}
