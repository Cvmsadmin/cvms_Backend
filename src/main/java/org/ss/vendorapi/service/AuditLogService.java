package org.ss.vendorapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.AuditLogEntity;
import org.ss.vendorapi.repository.AuditLogRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuditLogService {
	
	 @Autowired
	    private AuditLogRepository auditLogRepository;

	    public void logRequest(Long userId, String username, String apiName, String actionType, Object requestData) {
	        AuditLogEntity log = new AuditLogEntity();
	        log.setUserId(userId);
	        log.setUsername(username);
	        log.setApiName(apiName);
	        log.setActionType(actionType);

	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            log.setRequestData(mapper.writeValueAsString(requestData));
	        } catch (Exception e) {
	            log.setRequestData("Failed to serialize request data");
	        }

	        auditLogRepository.save(log);
	    }

}
