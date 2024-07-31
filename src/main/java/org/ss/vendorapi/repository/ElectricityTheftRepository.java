package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.modal.ElectricityTheftModel;
@Repository
public interface ElectricityTheftRepository extends JpaRepository<ElectricityTheftModel, Integer>{

}
