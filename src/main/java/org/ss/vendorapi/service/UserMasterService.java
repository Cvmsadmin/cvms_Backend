package org.ss.vendorapi.service;

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
	boolean authenticateByEmail(String email, String encode, String endConsumerRole);

}
