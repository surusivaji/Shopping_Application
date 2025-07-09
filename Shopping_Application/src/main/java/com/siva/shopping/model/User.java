package com.siva.shopping.model;

import java.util.ArrayList;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="User_Name", nullable=false, length=50)
	private String fullname;
	@Column(name="Mobile_Number", nullable=false, length=10, unique=true)
	private String mobileNumber;
	@Column(name="User_Email", nullable=false, length=50, unique=true)
	private String email;
	@Column(name="User_Address", nullable=false, length=200)
	private String address;
	@Column(name="User_City", nullable=false, length=50)
	private String city;
	@Column(name="User_State", nullable=false, length=50)
	private String state;
	@Column(name="Pincode", nullable=false, length=10)
	private String pincode;
	@Column(name="User_Password", nullable=false, length=20)
	private String password;
	@Column(name="Profile_Image", nullable=false)
	private String profileImage;

}
