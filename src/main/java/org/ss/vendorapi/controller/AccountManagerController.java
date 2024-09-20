package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;

@CrossOrigin("*")
@RestController
@RequestMapping("v2/api")
public class AccountManagerController {

	private final String ROLE_ID_ACCOUNT_MANAGER="2";

	@Autowired
	private UserMasterService userMasterService;

	@PostMapping("/getAllAccountManagers")
	public ResponseEntity<?> getAllAccountManagers(){
		Map<String,Object> statusMap=new HashMap<>();
		try {
			List<UserMasterEntity> users=userMasterService.findByRoleId(ROLE_ID_ACCOUNT_MANAGER);
			statusMap.put("users", users);
			statusMap.put(Parameters.statusMsg,  Constants.SUCCESS);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}catch(Exception ex) {
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
