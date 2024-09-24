package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.ss.vendorapi.entity.UserMasterEntity;


public interface UserCreationService {
	public UserMasterEntity save(UserMasterEntity userMasterEntity);
	public List<UserMasterEntity> getAllUsers();
	public boolean existsByEmail(String email);
	public boolean existsByPhone(String phone);
//	public UserMasterEntity findByEmail(String email);
	public Optional<UserMasterEntity> findByEmail(String email);
}