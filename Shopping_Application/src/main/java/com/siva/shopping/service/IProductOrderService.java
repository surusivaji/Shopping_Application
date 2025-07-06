package com.siva.shopping.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.siva.shopping.model.OrderRequest;
import com.siva.shopping.model.ProductOrder;
import com.siva.shopping.model.User;

public interface IProductOrderService {
	
	Boolean saveProductOrder(User user, OrderRequest orderRequest);
	
	List<ProductOrder> getProductOrdersByUser(User user);
	
	Page<ProductOrder> getProductOrderByUser(User user, int pageNo);
	
	Boolean updateOrderStatus(Integer orderId, String status);
	
	Page<ProductOrder> getAllProducts(int pageNo);
	
	ProductOrder getProductOrderByOrderId(String id);
	
	Boolean deleteProductOrder(ProductOrder productOrder);
	
	ProductOrder getProductOrderByOrderId(Integer id);
	
}
