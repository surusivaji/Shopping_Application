package com.siva.shopping.service;

import org.springframework.data.domain.Page;

import com.siva.shopping.model.User;

public interface IUserService {
	
	Boolean existsByEmail(String email);
	
	Boolean existsByMobileNumber(String mobileNumber);
	
	User saveUser(User user);
	
	User getUserByEmailAndPassword(String email, String password);
	
	Page<User> getAllUsers(int pageNo);
	
	User getUserById(int id);

	Boolean deleteUser(User user);
	
	User getUserByEmailAndMobileNumber(String email, String mobileNumber);

}
