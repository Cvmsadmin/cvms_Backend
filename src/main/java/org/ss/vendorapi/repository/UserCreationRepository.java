package org.ss.vendorapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.UserMasterEntity;


@Repository
public interface UserCreationRepository extends JpaRepository<UserMasterEntity, Long>{


	boolean existsByEmail(String email);
//	public boolean existsByEmail(String email);


	boolean existsByPhone(String phone);


	Optional<UserMasterEntity> findByEmail(String email);

	
}
