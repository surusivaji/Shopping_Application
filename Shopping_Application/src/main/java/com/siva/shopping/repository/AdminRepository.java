package com.siva.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.shopping.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	Admin findByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByMobileNumber(String mobileNumber);

}
