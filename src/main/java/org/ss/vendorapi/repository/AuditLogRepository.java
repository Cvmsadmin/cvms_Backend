package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ss.vendorapi.entity.AuditLogEntity;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long>{

}
