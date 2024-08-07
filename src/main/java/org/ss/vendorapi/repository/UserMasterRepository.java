package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.UserMasterEntity;



@Repository
public interface UserMasterRepository extends JpaRepository<UserMasterEntity, Long>{
	UserMasterEntity findByUserIdAndPassword(String userId, String password);
	UserMasterEntity findByPhoneAndPassword(String phone, String password);

	UserMasterEntity findByPhone(String phone);
	UserMasterEntity findByEmail(String email);
	UserMasterEntity findByUserId(String userId);


	final String WHERE_QUERY="select c from org.ss.vendorapi.entity.UserMasterEntity c where c.phone= :phone or c.email= :email ";
	@Query(WHERE_QUERY)
	UserMasterEntity findByMobileOrEmail(String phone, String email);
	
	UserMasterEntity findByEmailAndPhone(String email, String phone);
	

}
