//package org.ss.Jhatpat.repository;
//
//import java.sql.Timestamp;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import org.ss.Jhatpat.modal.CheckAttemptsModel;
//
//@Repository
//public interface CheckAttemptsRepository extends JpaRepository<CheckAttemptsModel, String> {
//
//	@Query("SELECT c FROM CheckAttemptsModel c where c.acc_Num=:acc_Num and c.discomname=:discomname") 
//	public CheckAttemptsModel getFlagDetails(@Param("acc_Num") String acc_Num,@Param("discomname") String discomname);
//
//	@Transactional
//	@Modifying
//	@Query("Update CheckAttemptsModel c set c.counter = :counter ,c.at_Flag =:at_Flag WHERE c.acc_Num = :acc_Num  and discomname=:discomname") 
//	public int updateFlag(@Param("counter") int counter,@Param("at_Flag") String at_Flag,@Param("acc_Num") String acc_Num,@Param("discomname") String discomname);
//	
//	@Transactional
//	@Modifying
//	@Query("Update CheckAttemptsModel set counter =:counter ,create_date=:created_date  WHERE acc_Num =:acc_Num  and discomname=:discomname")
//	public int updateColumns(@Param("counter") int counter,@Param("created_date") Timestamp created_date,@Param("acc_Num") String acc_Num,@Param("discomname") String discomname);
//
//	@Transactional
//	@Modifying
//	@Query("Update CheckAttemptsModel set counter =:counter  WHERE acc_Num =:acc_Num and discomname=:discomname")
//	public int updateCounter(@Param("counter") int counter,@Param("acc_Num") String acc_Num,@Param("discomname") String discomname);
//	
//}
//package org;

