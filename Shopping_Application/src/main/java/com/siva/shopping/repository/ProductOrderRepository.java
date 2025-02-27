package com.siva.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.ProductOrder;
import com.siva.shopping.model.User;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
	
	List<ProductOrder> findByUser(User user);
	
	ProductOrder findByOrderId(String orderId);

}
