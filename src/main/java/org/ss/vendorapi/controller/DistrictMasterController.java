package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.entity.DistrictMasterEntity;

import org.ss.vendorapi.service.DistrictMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class DistrictMasterController {

	@Autowired
	private DistrictMasterService districtMasterService;

	
	@EncryptResponse
	@PostMapping("/createDistrict")
	public ResponseEntity<?> createState(@RequestBody DistrictMasterEntity districtMasterDTO){
		Map<String,Object> statusMap= new HashMap<String,Object>();

		try {
			if(UtilValidate.isEmpty(districtMasterDTO.getDistrictName())) {
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}
			DistrictMasterEntity districtMaster=new DistrictMasterEntity();
			districtMaster.setDistrictName(districtMasterDTO.getDistrictName());

			districtMaster=districtMasterService.save(districtMaster);

			if(districtMaster!=null) {
				statusMap.put(Parameters.statusMsg,  StatusMessageConstants.DISTRICT_CREATED_SUCCESSFULLY);
				statusMap.put(Parameters.status, Constants.SUCCESS);
				statusMap.put(Parameters.statusCode, "RU_200");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
			}else {
				statusMap.put(Parameters.statusMsg,  StatusMessageConstants.DISTRICT_NOT_CREATED);
				statusMap.put(Parameters.status, Constants.FAIL);
				statusMap.put(Parameters.statusCode, "RU_301");
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
		}
		catch(Exception ex) {
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@EncryptResponse
	@GetMapping("/getAllDistricts")
	public ResponseEntity<?> getAllDistrict() {
		Map<String,Object> statusMap=new HashMap<>();
		try {

			List<DistrictMasterEntity> districtList = districtMasterService.findAll();

			statusMap.put("DistrictMasterEntity",districtList);

			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@EncryptResponse
	@GetMapping("/districtsByStateId")
	public ResponseEntity<?> getAllDistrictByStateId(@RequestParam String stateId) {
		Map<String,Object> statusMap=new HashMap<>(); 
		try {
			List<DistrictMasterEntity> districtMasterList=districtMasterService.getByStateId(stateId);
			statusMap.put("districtMasterEntity",districtMasterList);

			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

