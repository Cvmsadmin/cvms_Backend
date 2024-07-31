package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.modal.ProfileDataEntity;

@Repository
public interface ProfileDataRepository extends JpaRepository<ProfileDataEntity, String>{

}
