package org.ss.vendorapi.service;

import java.util.List;

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

	
	
	public List<UserMasterEntity> getAllUsers(){
		return creationUserRepository.findAll();
	}



	@Override
	public UserMasterEntity save(UserMasterEntity userMasterEntity) {
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
	

}
