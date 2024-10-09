package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.ss.vendorapi.entity.UserCreationEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
//import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.repository.UserCreationRepository;
//import org.ss.vendorapi.repository.UserMasterRepository;

@Service
public class UserCreationServiceImpl implements UserCreationService{
	@Autowired 
	private UserCreationRepository creationUserRepository;


	@Override
	public UserMasterEntity save(UserMasterEntity userMasterEntity) {
		userMasterEntity.setActive(1);
		userMasterEntity.setCreateDate(new Date());
		return creationUserRepository.save(userMasterEntity);
	}

	@Override
	public boolean existsByEmail(String email) {
		return creationUserRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return creationUserRepository.existsByPhone(phone);
	}    

	// Method to find a user by email
	public Optional<UserMasterEntity> findByEmail(String email) {
		return creationUserRepository.findByEmail(email);
	}



	@Override
	public UserMasterEntity update(UserMasterEntity userMasterEntity) {
		userMasterEntity.setUpdateDate(new Date());
		return creationUserRepository.save(userMasterEntity);
	}



	@Override
	public List<UserMasterEntity> findAll() {
		return creationUserRepository.findAll();
	}



	@Override
	public UserMasterEntity findById(Long id) {
		return creationUserRepository.findById(id).orElse(null);
	}

}
