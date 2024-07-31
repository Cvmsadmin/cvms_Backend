package org.ss.vendorapi.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.config.CustomUserDetails;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.exceptions.UserNotFoundException;
import org.ss.vendorapi.modal.User;
import org.ss.vendorapi.repository.UserMasterRepository;
import org.ss.vendorapi.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserMasterEntity user = userMasterRepository.findByEmail(userId);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + userId);
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

	public UserDetails loadUserByKnoAndDiscom(String kno, String discomName) throws UsernameNotFoundException {
		User objUser = userRepository.findByKnoAndDiscomName(kno, discomName)
				.orElseThrow(() -> new UserNotFoundException("User not found!!"));
		objUser.setUsername(kno);
		return objUser;
	}

	public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		UserMasterEntity user = userMasterRepository.findByUserId(userId);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + userId);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
				Collections.emptyList());
	}
	
	public UserDetails loadUserByMobileNumber(String mobileNumber) throws UsernameNotFoundException {
		UserMasterEntity user = userMasterRepository.findByPhone(mobileNumber);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + mobileNumber);
		}
		return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
				Collections.emptyList());
	}

	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		UserMasterEntity user = userMasterRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				Collections.emptyList());
	}

	

}
