package org.ss.vendorapi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "audit_log")
public class AuditLogEntity {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId;
	    private String username;
	    private String apiName;
	    private String actionType;     // INSERT, UPDATE, DELETE

	    @Lob
	    private String requestData;

	    private LocalDateTime timestamp = LocalDateTime.now();

	

}
