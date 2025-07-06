package com.siva.shopping.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.siva.shopping.model.Cart;
import com.siva.shopping.model.OrderAddress;
import com.siva.shopping.model.OrderRequest;
import com.siva.shopping.model.OrderStatus;
import com.siva.shopping.model.Product;
import com.siva.shopping.model.ProductOrder;
import com.siva.shopping.model.User;
import com.siva.shopping.repository.CartRepository;
import com.siva.shopping.repository.ProductOrderRepository;
import com.siva.shopping.repository.ProductRepository;

@Service
public class IProductOrderServiceImpl implements IProductOrderService {
	
	@Autowired
	private ProductOrderRepository productOrderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Boolean saveProductOrder(User user, OrderRequest orderRequest) {
		try {
			List<Cart> cartList = cartRepository.findByUser(user);
			Boolean isSaved  = false;
			for (Cart cart : cartList) {
				ProductOrder productOrder = new ProductOrder();
				Random random = new Random();
				Long orderId = random.nextLong(10000000000L);
				if (orderId < 10000000000L) {
					orderId = orderId+10000000000L;
				}
				productOrder.setOrderId(String.valueOf(orderId));
				productOrder.setOrderDate(Date.valueOf(LocalDate.now()));
				productOrder.setProduct(cart.getProduct());
				productOrder.setQuantity(cart.getQuantity());
				productOrder.setPrice(cart.getProduct().getDiscountPrice());
				productOrder.setUser(cart.getUser());
				productOrder.setStatus(OrderStatus.IN_PROGRESS.getName());
				productOrder.setPaymentType(orderRequest.getPaymentType());
				OrderAddress orderAddress = new OrderAddress();
				orderAddress.setFirstName(orderRequest.getFirstName());
				orderAddress.setLastName(orderRequest.getLastName());
				orderAddress.setEmail(orderRequest.getEmail());
				orderAddress.setMobileNumber(orderRequest.getMobileNumber());
				orderAddress.setAddress(orderRequest.getAddress());
				orderAddress.setState(orderRequest.getState());
				orderAddress.setCity(orderRequest.getCity());
				orderAddress.setPincode(orderRequest.getPincode());
				productOrder.setOrderAddress(orderAddress);
				ProductOrder save = productOrderRepository.save(productOrder);
				if (save!=null) {
					isSaved = true;
					Product product = cart.getProduct();
					product.setStock(product.getStock()-save.getQuantity());
					Product isUpdated = productRepository.save(product);
					if (isUpdated==null) {
						isSaved = false;
					}
				}
			}
			if (isSaved) {
				cartRepository.deleteAll(cartList);
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<ProductOrder> getProductOrdersByUser(User user) {
		try {
			List<ProductOrder> list = productOrderRepository.findByUser(user);
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Page<ProductOrder> getProductOrderByUser(User user, int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 5, Sort.by(Sort.Direction.DESC, "id"));
			Page<ProductOrder> page = productOrderRepository.findByUser(user, pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean updateOrderStatus(Integer orderId, String status) {
		try {
			ProductOrder productOrder = productOrderRepository.findById(orderId).get();
			boolean isSave = false;
			if (status.equalsIgnoreCase("Cancelled")) {
				Product product = productOrder.getProduct();
				product.setStock(productOrder.getQuantity()+product.getStock());
				Product save = productRepository.save(product);
				if (save!=null) {
					isSave = true;
				} 
				else {
					isSave = false;
				}
			}
			else {
				isSave = true;
			}
			productOrder.setStatus(status);
			ProductOrder save = productOrderRepository.save(productOrder);
			if (save!=null && isSave) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Page<ProductOrder> getAllProducts(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 5);
			Page<ProductOrder> page = productOrderRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public ProductOrder getProductOrderByOrderId(String orderId) {
		try {
			ProductOrder productOrder = productOrderRepository.findByOrderId(orderId);
			if (productOrder!=null) {
				return productOrder;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ProductOrder getProductOrderByOrderId(Integer id) {
		try {
			Optional<ProductOrder> optional = productOrderRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean deleteProductOrder(ProductOrder productOrder) {
		try {
			productOrderRepository.delete(productOrder);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
