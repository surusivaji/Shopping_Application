package com.siva.shopping.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.siva.shopping.model.User;
import com.siva.shopping.repository.UserRepository;

@Service
public class IUserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean existsByEmail(String email) {
		try {
			Boolean emailStatus = userRepository.existsByEmail(email);
			return emailStatus;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Boolean existsByMobileNumber(String mobileNumber) {
		try {
			Boolean mobileNumberStatus = userRepository.existsByMobileNumber(mobileNumber);
			return mobileNumberStatus;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public User saveUser(User user) {
		try {
			User save = userRepository.save(user);
			return save;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		try {
			User user = userRepository.findByEmailAndPassword(email, password);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Page<User> getAllUsers(int pageNo) {
		try {
			Pageable pageable = PageRequest.of(pageNo, 10);
			Page<User> page = userRepository.findAll(pageable);
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public User getUserById(int id) {
		try {
			Optional<User> optional = userRepository.findById(id);
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
	public Boolean deleteUser(User user) {
		try {
			userRepository.delete(user);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public User getUserByEmailAndMobileNumber(String email, String mobileNumber) {
		try {
			User user = userRepository.findByEmailAndMobileNumber(email, mobileNumber);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

}
