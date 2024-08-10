package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.UserMasterEntity;



public interface UserCreationService {
	public UserMasterEntity save(UserMasterEntity userMasterEntity);
	public List<UserMasterEntity> getAllUsers();
	public boolean existsByEmail(String email);
	public boolean existsByPhone(String phone);
}