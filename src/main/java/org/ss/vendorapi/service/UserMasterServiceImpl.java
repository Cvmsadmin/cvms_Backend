package org.ss.vendorapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.repository.UserMasterRepository;

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserMasterRepository userMasterRepository;

	private static final Class<?> CLASS_NAME = UserMasterServiceImpl.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Override
	public Boolean checkUserInPortal(UserMasterEntity userSetUpEntity) throws RequestNotFoundException {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
		try {
//			logger.logMethodStart(methodName);
			UserMasterEntity registerUserEntity = userMasterRepository.findByMobileOrEmail(userSetUpEntity.getPhone(),
					userSetUpEntity.getEmail());

//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in RegisterUser entity table existance by mobile number or email . : " + registerUserEntity);
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, " RegisterUserEntity MOBILE NUMBER ::: "+registerUserEntity.getPhone());
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "RegisterUserEntity EMAIL ::: "+registerUserEntity.getEmail());

			/* IF USER NOT NULL THEN USER ALREADY EXIST */
			if (registerUserEntity != null) {
				return true;
			}
		} catch (Exception ex) {
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "RegisterUserEntity ::: "+userSetUpEntity.getEmail());
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "RegisterUserEntity Exception  ::: "+ex.getMessage());
		}
//		logger.logMethodEnd(methodName);
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
	public boolean authenticateByEmail(String email, String encode, String endConsumerRole) {
		String methodName = "checkUserInPortal(RegisterUserEntity userSetUpEntity)";
//		logger.logMethodStart(methodName);
		String status = null;
		UserMasterEntity chkUserInPortalByUserId = userMasterRepository.findByEmail(email);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in RegisterUser entity table existance by user id. : " + chkUserInPortalByUserId);

//		System.out.println("password..." + encode);

		if (null != chkUserInPortalByUserId && encode.equals(chkUserInPortalByUserId.getPassword())) {
			return true;
		} else {
			return false;
		}
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

}
