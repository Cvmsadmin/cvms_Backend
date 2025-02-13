package org.ss.vendorapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.apache.catalina.User;
//import org.apache.catalina.realm.JNDIRealm.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
import org.ss.vendorapi.repository.UserMasterRepository;

//import io.jsonwebtoken.lang.Arrays;

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserMasterRepository userMasterRepository;

	private static final Class<?> CLASS_NAME = UserMasterServiceImpl.class;

	@Override
	public Boolean checkUserInPortal(UserMasterEntity userSetUpEntity) throws RequestNotFoundException {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
		try {
			UserMasterEntity registerUserEntity = userMasterRepository.findByMobileOrEmail(userSetUpEntity.getPhone(),
					userSetUpEntity.getEmail());


			/* IF USER NOT NULL THEN USER ALREADY EXIST */
			if (registerUserEntity != null) {
				return true;
			}
		} catch (Exception ex) {
		}
		return false;
	}

	@Override
	public String saveUser(UserMasterEntity userSetUpEntity) throws RequestNotFoundException {
		String saveStatus = "false";
		userSetUpEntity.setPassword(passwordEncoder.encode(userSetUpEntity.getPassword()));
		UserMasterEntity vallidateDetailsFormDTO = userMasterRepository.save(userSetUpEntity);
		if (null != vallidateDetailsFormDTO) {
			saveStatus = "true";
		} else {
			saveStatus = "false";
		}
		return saveStatus;

	}

	@Override
	public boolean authenticateByUserId(String userId, String password, String role) {

		UserMasterEntity user = userMasterRepository.findByUserId(userId);
		return user != null && user.getPassword().equals(password) && role.equals(user.getRole());
	}

	@Override
	public boolean authenticateByMobileNumber(String mblNumber, String password, String role) {

		UserMasterEntity user = userMasterRepository.findByPhone(mblNumber);
		return user != null && user.getPassword().equals(password) && role.equals(user.getRole());
	}

	@Override
	public String checkUserInPortalByUserId(String userId) throws RequestNotFoundException {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
//		logger.logMethodStart(methodName);
		String status = null;
		UserMasterEntity chkUserInPortalByUserId = userMasterRepository.findByUserId(userId);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in RegisterUser entity table existance by user id. : " + chkUserInPortalByUserId);

		if (null == chkUserInPortalByUserId) {
			status = "false";
		} else {
			status = "true";
		}
//		logger.logMethodEnd(methodName);
		return status;
	}

	@Override
	public String checkUserInPortalByMobileNumber(String mobileNumber) throws RequestNotFoundException {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
//		logger.logMethodStart(methodName);
		String status = null;
		UserMasterEntity chkUserInPortalByMobileNumber = userMasterRepository.findByPhone(mobileNumber);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in RegisterUser entity table existance by mobile number. : " + chkUserInPortalByMobileNumber);

		if (null == chkUserInPortalByMobileNumber) {
			status = "false";
		} else {
			status = "true";
		}
//		logger.logMethodEnd(methodName);
		return status;
	}

	@Override
	public String checkUserInPortalByUserIdAndPassword(String userId, String password) throws RequestNotFoundException {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
//		logger.logMethodStart(methodName);
		String status = null;
		UserMasterEntity chkUserInPortalByUserId = userMasterRepository.findByUserIdAndPassword(userId, password);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in RegisterUser entity table existance by user id and password. : " + chkUserInPortalByUserId);

		if (null == chkUserInPortalByUserId) {
			status = "false";
		} else {
			status = "true";
		}
//		logger.logMethodEnd(methodName);
		return status;
	}

	@Override
	public String checkUserInPortalByMobileNumberAndPassword(String mobileNumber, String password)
			throws RequestNotFoundException {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
//		logger.logMethodStart(methodName);
		String status = null;
		UserMasterEntity chkUserInPortalByMobileNumber = userMasterRepository.findByPhoneAndPassword(mobileNumber,
				password);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in RegisterUser entity table existance by mobile number and password. : " + chkUserInPortalByMobileNumber);

		if (null == chkUserInPortalByMobileNumber) {
			status = "false";
		} else {
			status = "true";
		}
//		logger.logMethodEnd(methodName);
		return status;
	}

	@Override
	public void updatePassword(String mobileNumber, String newPassword) throws RequestNotFoundException {
		UserMasterEntity user = userMasterRepository.findByPhone(mobileNumber);
		if (user == null) {
			throw new RequestNotFoundException("user not found");
		} else {
			user.setPassword(newPassword);
			userMasterRepository.save(user);
		}

	}

	@Override
	public String generateUserID(String mobileNumber, String methodName, String module) {
		String userId = "PCL" + mobileNumber;
		return userId;
	}

	@Override
	public UserMasterEntity authenticateByEmail(String email, String encode) {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
		String status = null;
		UserMasterEntity userEntity = userMasterRepository.findByEmail(email);
		return userEntity;
	}

	 public boolean updatePasswordByEmailAndPhone(String email, String phone, String newPassword) {
	        UserMasterEntity user = userMasterRepository.findByEmailAndPhone(email, phone);
	        if (user != null) {
	            user.setPassword(passwordEncoder.encode(newPassword));
	            userMasterRepository.save(user);
	            return true;
	        }
	        return false;
	    }

	@Override
	public List<UserMasterEntity> findByRoleId(String roleId) {
		return userMasterRepository.findByRole(roleId);
	}

	@Override
	public List<UserMasterEntity> findAll() {
		return userMasterRepository.findAll();
	}

	@Override
	public UserMasterEntity findById(Long id) {
		return userMasterRepository.findById(id).orElse(null);
	}

	@Override
	public UserMasterEntity findByEmailAndPhone(String email, String phone) {
	    return userMasterRepository.findByEmailAndPhone(email, phone);
	}


	@Override
	public UserMasterEntity findByEmail(String email) {
		return userMasterRepository.findByEmail(email);
	}

	@Override
	public void save(UserMasterEntity user) {
		userMasterRepository.save(user);
	}

	
	
//	@Override
//	public List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName) {
//	    // Initialize an empty list to hold the authorized email addresses
//	    List<String> authorizedEmails = new ArrayList<>();
//
//	    try {
//	        // 1. Query the database for users with roles of Account Manager, Project Manager, and Management
//	        // 2. Based on the given clientName and projectName
//	        
//	        // Assuming you have a repository or service to query the roles
//	        List<org.apache.catalina.User> users = userMasterRepository.findUsersByclientNameAndprojectName(clientName, projectName, 
//	              Arrays.asList("Account Manager", "Project Manager", "Management"));
//
//
//	        // 3. Extract the emails of these users and add them to the authorizedEmails list
//	        for (User user : users) {
//	            authorizedEmails.add(((UserMasterEntity) user).getEmail());
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace(); // Handle exceptions if necessary (e.g., logging)
//	    }
//
//	    // Return the list of authorized emails
//	    return authorizedEmails;
//	}
}
