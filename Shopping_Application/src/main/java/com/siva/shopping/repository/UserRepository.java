package com.siva.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findByEmailAndMobileNumber(String email, String mobileNumber);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByMobileNumber(String mobileNumber);

}
