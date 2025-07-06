package com.siva.shopping.model;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ProductOrder {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String orderId;
	private Date orderDate;
	@ManyToOne
	private Product product;
	private Integer quantity;
	private Double price;
	@ManyToOne
	private User user;
	private String status;
	private String paymentType;
	@OneToOne(cascade = CascadeType.ALL)
	private OrderAddress orderAddress;
}
