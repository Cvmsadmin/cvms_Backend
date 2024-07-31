//package org.ss.vendorapi.service;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//import org.ss.vendorapi.entity.ConsumerMasterEntity;
//import org.ss.vendorapi.exceptions.RequestNotFoundException;
//import org.ss.vendorapi.logging.UPPCLLogger;
//import org.ss.vendorapi.modal.LoginResponseModal;
//import org.ss.vendorapi.repository.ConsumerMasterRepository;
//
//
//@Service
//public class ConsumerMasterServiceImpl implements ConsumerMasterService {
//	private static final Class<?> CLASS_NAME = ConsumerMasterServiceImpl.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());
//	
//	@Autowired private ConsumerMasterRepository consumerMasterRepository;	
//	@Autowired private Environment env;	
//	@PersistenceContext private EntityManager entityManager;
//
//	@Override
//	public void update(ConsumerMasterEntity consumerMasterEntity) {
//		consumerMasterEntity.setModifiedDate(new Date());
//		consumerMasterRepository.save(consumerMasterEntity);	
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ConsumerMasterEntity> findByWhere(String where) {
//		List<ConsumerMasterEntity> list=null;
//		try{
//			Query query=null;
//			if(where!=null)
//				query=entityManager.createQuery("FROM ConsumerMasterEntity o WHERE "+where);
//			list=query.getResultList();
//		}catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		return list;
//	}
//	
//	public String checkUserInPortal(ConsumerMasterEntity userSetUpEntity) throws RequestNotFoundException{
//		String methodName = "checkUserInPortal(ConsumerMasterEntity userSetUpEntity)";
//		logger.logMethodStart(methodName);
//		String knoValueStatus=null;
//		ConsumerMasterEntity chkUserInPortal = consumerMasterRepository.findByMobileNoAndEmail(userSetUpEntity.getMobileNo(), userSetUpEntity.getEmail());
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Check User in consumer master entity table existance. : " + chkUserInPortal);
//		if(null==chkUserInPortal) {
//			knoValueStatus="false";
//		}else {
//			knoValueStatus="true";
//		}
//		logger.logMethodEnd(methodName);
//		return knoValueStatus;
//	}
//	
//	public String saveUser(ConsumerMasterEntity userSetUpEntity) throws RequestNotFoundException
//	{ 
//		String saveStatus="false";
//		ConsumerMasterEntity vallidateDetailsFormDTO=consumerMasterRepository.save(userSetUpEntity);
//		if(null!=vallidateDetailsFormDTO)
//		{
//			saveStatus="true";
//		}else{
//			saveStatus="false";
//		}
//		return saveStatus;
//	}
//	
//	public String updateEmail(ConsumerMasterEntity verifyOTPModel) throws RequestNotFoundException
//	{ 
//		String saveStatus="false";
//		consumerMasterRepository.updateEmailConfStatus("false",verifyOTPModel.getKno(),verifyOTPModel.getDiscomName());
//		return saveStatus;
//	}
//
//	@Override
//	public ConsumerMasterEntity getConsumerDetailsByKNo(ConsumerMasterEntity userSetUpEntity)throws RequestNotFoundException {
//		ConsumerMasterEntity repFromDB =consumerMasterRepository.findByKnoAndDiscomName(userSetUpEntity.getKno(),userSetUpEntity.getDiscomName());
//		return repFromDB;
//	}
//	
//	@Override
//	public LoginResponseModal authenticateUser(ConsumerMasterEntity userSetUpEntity) throws RequestNotFoundException{
//		ConsumerMasterEntity objLoginRequestModal = consumerMasterRepository.findByKnoAndDiscomNameAndUpassword(String.valueOf(userSetUpEntity.getKno()), userSetUpEntity.getDiscomName(),userSetUpEntity.getUpassword());
//		if(objLoginRequestModal != null){
//			LoginResponseModal objLoginResponseModal = new LoginResponseModal();
//			objLoginResponseModal.setKno(String.valueOf(objLoginRequestModal.getKno()));
//			objLoginResponseModal.setDiscomName(objLoginRequestModal.getDiscomName());
//			objLoginResponseModal.setEmailId(objLoginRequestModal.getEmail());
//			objLoginResponseModal.setMobile(objLoginRequestModal.getMobileNo());
//			objLoginResponseModal.setUpassword(objLoginRequestModal.getPassword());	
//			objLoginResponseModal.setSecretQuestion(objLoginRequestModal.getSecurityQuestion());
//			objLoginResponseModal.setSecretAnswer(objLoginRequestModal.getSecurityAnswer());
//			objLoginResponseModal.setConfirmemail(objLoginRequestModal.getConfirmEmail());
//			objLoginResponseModal.setLastLogin(objLoginRequestModal.getLastLogin());
//			objLoginResponseModal.setSource_of_Registration(objLoginRequestModal.getSourceOfRegistration());
//			objLoginResponseModal.setOtp(objLoginRequestModal.getOtp());
//			return objLoginResponseModal;
//		}
//		else{
//			return null;
//		}
//	}
//
//}