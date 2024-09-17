package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ss.vendorapi.entity.FileUploadRequestModal;

public interface DocumentUploadRepository extends JpaRepository<FileUploadRequestModal, Long> {
	
	@Query(value = "select INFRA_COST_A from addserandservicelinecrgs where LOAD= :load and CLAUSE='A'  ", nativeQuery = true)
	public Integer getServiceLineChargesByClauseA(@Param(value = "load") int load);
	
	
	boolean existsByApplicantIdAndDocumentType(String applicantId, FileUploadRequestModal.DocumentType documentType);
	

}
