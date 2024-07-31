//package org.ss.vendorapi.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import org.ss.vendorapi.entity.ConsumerMasterEntity;
//
//
//@Repository
//public interface ConsumerMasterRepository extends JpaRepository<ConsumerMasterEntity, String>{
//
//	ConsumerMasterEntity findByKnoAndDiscomName(String kno, String discomName);
//	ConsumerMasterEntity findByMobileNoAndEmail(Long mobileNo,String email);
//	
//	@Modifying
//    @Transactional
//	@Query("update ConsumerMasterEntity v  set v.confirmEmail=:status where v.kno=:kno and discomName=:discom")
//	int updateEmailConfStatus(String status,String kno,String discom);	
//		
//	@Modifying
//    @Transactional
//    @Query("update ConsumerMasterEntity v  set v.confirmMobile=:status where v.kno=:kno and discomName=:discom")    
//	int updateMobileConfStatus(String status,String kno,String discom);
//	
//	ConsumerMasterEntity findByKnoAndDiscomNameAndUpassword(String kno, String discomName, String upassword);
//	
////	@Query(value ="select c.confirmEmail from ConsumerMasterEntity c where c.kno=:Acc_ID and c.discomName=:discom")
////	public String getConfirmEmail(@Param("Acc_ID")String Acc_ID,@Param("discom") String discom);
//	
////	ConsumerMasterEntity findByKnoandDiscomName(String kno, String discomName);
//
////	@Query(value ="select c.confirmEmail from ConsumerMasterEntity c where c.kno=:Acc_ID and c.discomName=:discom")
////	public String getConfirmEmail(@Param("Acc_ID")String Acc_ID,@Param("discom") String discom);
//	
////	@Query(value ="select c.confirmEmail from ConsumerMasterEntity c where c.kno=:Acc_ID and c.discomName=:discom")
////	public String getConfirmEmail(@Param("Acc_ID")String Acc_ID,@Param("discom") String discom);
//	
//	@Query(value="select l.confirmEmail from ConsumerMasterEntity l where l.kno=:Acc_ID and l.discomName=:discom")
//	public String getConfirmEmail(@Param("Acc_ID")String Acc_ID,@Param("discom") String discom);
//	
//	@Query(value="select lastLogin from ConsumerMasterEntity where kno=:Acc_ID and discomName=:discom")
//	public String getLastLogin(@Param("Acc_ID")String Acc_ID,@Param("discom") String discom);
//	
//	@Transactional
//	@Modifying
//	@Query(value="update ConsumerMasterEntity c set c.lastLogin=:lastLogin where c.kno=:Acc_ID and c.discomName=:discom")
//	public int updateLastLogin(@Param("lastLogin")String lastLogin,@Param("Acc_ID")String Acc_ID,@Param("discom") String discom);
//	
//	//List<ConsumerMasterEntity> findByKno(String kno);
//	
//	@Modifying
//    @Transactional
//	@Query("update ConsumerMasterEntity v  set v.upassword=:password where v.kno=:kno and v.discomName=:discomName")
//	int updatePassword(String password,String kno,String discomName);
//	
//	 @Transactional
//	 @Modifying
//	 @Query("delete from ConsumerMasterEntity c where c.kno=?1 and c.discomName=?2")
//	    void deleteByKnoAndDiscom(String kno,String discomName);
//	 
//	 
//	 @Modifying
//	 @Transactional
//	 @Query("update ConsumerMasterEntity v  set v.mobileNo=:mobNo where v.kno=:kno and v.discomName=:discomName")
//	 int updateMobileNo(Long mobNo,String kno,String discomName);
//	
//}