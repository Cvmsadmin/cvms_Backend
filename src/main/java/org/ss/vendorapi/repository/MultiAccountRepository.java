package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ss.vendorapi.modal.MultiAccountEntity;

public interface MultiAccountRepository extends JpaRepository<MultiAccountEntity, Long> {

	@Query(value = "SELECT * FROM MULTIPLE_ACCOUNTS_DETAILS WHERE SECONDRY_ACCOUNT = ?1  AND DISCOM = ?2 AND STATUS = 'E'", nativeQuery = true)
	MultiAccountEntity findUserBySecondryAcc(String secondryAccount, String discom);

	@Query(value = "SELECT count(*) FROM MULTIPLE_ACCOUNTS_DETAILS WHERE PRIMARY_ACCOUNT = ?1 AND DISCOM = ?2 AND STATUS = 'E'", nativeQuery = true)
	int findUsersCountByPrimaryAcc(String primaryAccount, String discome);

	@Query(value = "SELECT secondry_account FROM MULTIPLE_ACCOUNTS_DETAILS WHERE PRIMARY_ACCOUNT = ?1 AND DISCOM = ?2 and STATUS = 'E'", nativeQuery = true)
	List<String> findUsersByPrimaryAcc(String primaryAccount, String discom);
	
	@Query(value = "SELECT * FROM MULTIPLE_ACCOUNTS_DETAILS WHERE PRIMARY_ACCOUNT = ?1 AND SECONDRY_ACCOUNT = ?2 AND DISCOM = ?3 AND STATUS = 'E'", nativeQuery = true)
	MultiAccountEntity findByPrimaryAndSecondryAccount(String primaryAccount, String secondryAccount, String discom);
	
}