package com.siva.shopping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="Fullname", nullable=false, length=50)
	private String fullName;
	@Column(name="Mobile_Number", nullable=false, length=10, unique=true)
	private String mobileNumber;
	@Column(name="Email", nullable=false, length=50, unique=true)
	private String email;
	@Column(name="Address", nullable=false, length=200)
	private String address;
	@Column(name="City", nullable=false, length=50)
	private String city;
	@Column(name="State", nullable=false, length=50)
	private String state;
	@Column(name="Pincode", nullable=false, length=10)
	private String pincode;
	@Column(name="Password", nullable=false, length=20)
	private String password;
	@Column(name="profileImage", nullable=false)
	private String profileImage;


}
