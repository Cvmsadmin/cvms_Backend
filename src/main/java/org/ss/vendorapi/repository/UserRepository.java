package org.ss.vendorapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ss.vendorapi.modal.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByKnoAndDiscomNameAndPassword(String kno, String discomName, String password);
		
	Optional<User> findByKnoAndDiscomName(String kno, String discomName);
	
    Boolean existsByKno(String kno);

    Boolean existsByEmailId(String email);
	
}
