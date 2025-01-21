package org.ss.vendorapi.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ProjectPaymentSummary;

@Repository
public interface ProjectBillingSummaryRepository extends JpaRepository<ProjectPaymentSummary, Long> {

	@Query("SELECT p FROM ProjectPaymentSummary p")
	List<ProjectPaymentSummary> findAllProjectPaymentSummary();
	
	
	
//	   @Query("SELECT p FROM ProjectBillingSummary p WHERE p.projectName = :projectName AND p.totalBillableAmount > :amount")
//	    List<ProjectBillingSummary> findProjectBillingSummariesByProjectNameAndAmount(
//	        @Param("projectName") String projectName, @Param("amount") Double amount
//	    );
	
	
//	
//	
//    List<ProjectBillingSummary> findByProjectName(String projectName);
//
//
//    List<ProjectBillingSummary> findByProjectNameAndTotalBillableAmountGreaterThan(
//        String projectName, Double amount
//    );

}
