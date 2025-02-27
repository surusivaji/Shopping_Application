package com.siva.shopping.service;

import org.springframework.data.domain.Page;

import com.siva.shopping.model.Admin;

public interface IAdminService {
	
	Admin saveAdmin(Admin admin);
	
	Page<Admin> getAllAdmins(int pageNo);

	Admin getAdminByEmailAndPassword(String email, String password);
	
	Admin getAdminById(Integer id);
	
	Boolean deleteAdmin(Admin admin);
	
}
