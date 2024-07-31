//package org.ss.vendorapi.service;
//
//import java.util.List;
//
//import org.ss.vendorapi.entity.ConsumerMasterEntity;
//import org.ss.vendorapi.exceptions.RequestNotFoundException;
//import org.ss.vendorapi.modal.LoginResponseModal;
//
//public interface ConsumerMasterService {
//	
//	public void update(ConsumerMasterEntity consumerMasterEntity);
//	public List<ConsumerMasterEntity> findByWhere(String where);	
//	String checkUserInPortal(ConsumerMasterEntity userSetUpEntity) throws RequestNotFoundException;
//	String saveUser(ConsumerMasterEntity userSetUpEntity) throws RequestNotFoundException;
//	String updateEmail(ConsumerMasterEntity verifyOTPModel)throws RequestNotFoundException;
//	ConsumerMasterEntity getConsumerDetailsByKNo(ConsumerMasterEntity userSetUpEntity)throws RequestNotFoundException;	
//    LoginResponseModal authenticateUser(ConsumerMasterEntity userSetUpEntity) throws RequestNotFoundException;  
//}
