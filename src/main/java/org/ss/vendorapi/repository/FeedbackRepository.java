package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.modal.FeedbackModel;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, String> {

//	@Query(value="select f from FeedbackModel f where f.accNo=:accNo and f.servConnNo=:servConnNo")
	public List<FeedbackModel> findByAccNoAndServConnNo(String accNo,String servConnNo);

	//Need to create table feedback
}
