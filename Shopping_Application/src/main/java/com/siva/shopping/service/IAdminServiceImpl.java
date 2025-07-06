package com.siva.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.siva.shopping.model.Admin;
import com.siva.shopping.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class IAdminServiceImpl implements IAdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public Boolean checkEmailIsPresentOrNot(String email) {
		try {
			Boolean emailStatus = adminRepository.existsByEmail(email);
			return emailStatus;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Boolean checkMobileNumberIsPresentOrNot(String mobileNumber) {
		try {
			Boolean mobileNumberStatus = adminRepository.existsByMobileNumber(mobileNumber);
			return mobileNumberStatus;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Admin saveAdmin(Admin admin) {
		try {
			Admin save = adminRepository.save(admin);
			return save;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Admin getAdminByEmailAndPassword(String email, String password) {
		try {
			Admin admin = adminRepository.findByEmailAndPassword(email, password);
			if (admin!=null) {
				return admin;
			}
			else {
				return null;
			}
		}  catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Page<Admin> getAllAdmins(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 8);
			Page<Admin> page = adminRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Admin getAdminById(Integer id) {
		try {
			Admin admin = adminRepository.findById(id).get();
			if (admin!=null) {
				return admin;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean deleteAdmin(Admin admin) {
		try {
			adminRepository.delete(admin);
			return true;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("successMsg");
		session.removeAttribute("failMsg");
		session.removeAttribute("logoutInfo");
		session.removeAttribute("warningMsg");
		session.removeAttribute("errorInfo");
		session.removeAttribute("updateSuccess");
		session.removeAttribute("updateFail");
		session.removeAttribute("infoMsg");
	}

}
