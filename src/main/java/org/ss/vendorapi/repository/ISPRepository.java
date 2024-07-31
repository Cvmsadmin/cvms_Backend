package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.modal.ISPModel;

@Repository
public interface ISPRepository extends JpaRepository<ISPModel, String> {

}
