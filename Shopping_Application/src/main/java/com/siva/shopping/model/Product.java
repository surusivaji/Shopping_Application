package com.siva.shopping.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="Product_Title", length=100, nullable = false)
	private String title;
	@Column(name="Product_Description", length=2000, nullable = false)
	private String description;
	@Column(name="Product_Category", length=100, nullable = false)
	private String category;
	@Column(name="Product_Price", nullable = false)
	private Double price;
	@Column(name="Product_Stock", nullable = false)
	private Integer stock;
	@Column(name="Product_Image", nullable = false)
	private String image;
	@Column(name="Product_Discount", nullable = false)
	private Integer discount;
	@Column(name="Product_Discount_Price",nullable = false)
	private Double discountPrice;
	@Column(name="Is_Active", nullable = false)
	private Boolean isActive;
	@OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Cart> cartItems;
	 @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	 private List<ProductOrder> orderItems;

}
