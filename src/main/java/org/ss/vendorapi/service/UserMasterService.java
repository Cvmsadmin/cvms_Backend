package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.exceptions.RequestNotFoundException;

public interface UserMasterService {
	
	Boolean checkUserInPortal(UserMasterEntity userSetUpEntity) throws RequestNotFoundException;
	String checkUserInPortalByUserId(String userId) throws RequestNotFoundException;
	String checkUserInPortalByUserIdAndPassword(String userId,String password) throws RequestNotFoundException;
	String checkUserInPortalByMobileNumber(String mobileNumber) throws RequestNotFoundException;
	String checkUserInPortalByMobileNumberAndPassword(String mobileNumber,String password) throws RequestNotFoundException;
	String saveUser(UserMasterEntity userSetUpEntity) throws RequestNotFoundException;
	public boolean authenticateByUserId(String userId, String password,String role);
	public boolean authenticateByMobileNumber(String mblNumber, String password,String role);
	public void updatePassword(String mobileNumber, String newPassword) throws RequestNotFoundException;
	
	/* THIS METHOD IS TO GENERATE USER ID FOR END CONSUMER AT REGISTRATION TIME */
	public String generateUserID(String mobileNumber, String methodName, String module);
	public UserMasterEntity authenticateByEmail(String email, String encode);
	public boolean updatePasswordByEmailAndPhone(String email, String phone, String encodedPassword);
	
	public List<UserMasterEntity> findByRoleId(String roleId);
	public List<UserMasterEntity> findAll();
	public UserMasterEntity findById(Long id);
	UserMasterEntity findByEmailAndPhone(String email, String phone);
	UserMasterEntity findByEmail(String email);
	void save(UserMasterEntity user);
	

//	List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName);
	
	
	

}
