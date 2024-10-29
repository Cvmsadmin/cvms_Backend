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
import org.ss.vendorapi.entity.CityMasterEntity;
import org.ss.vendorapi.service.CityMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;

@RestController
@CrossOrigin("*")
@RequestMapping("v2/api")
public class CityMasterController {

	@Autowired
	private CityMasterService cityMasterService;

	@EncryptResponse
	@PostMapping("/saveCity")
	public ResponseEntity<?>  saveCity(@RequestBody CityMasterEntity cityMasterEntity){
		Map<String,Object> statusMap=new HashMap<>();
		try {
			cityMasterService.save(cityMasterEntity);
			
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.DISTRICT_CREATED_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}catch(Exception ex) {
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@EncryptResponse
	@GetMapping("/getAllCities")
	public ResponseEntity<?> getCityById(@RequestParam String districtId ){
		Map<String,Object> statusMap=new HashMap<>();
		try {
			List<CityMasterEntity> cityList=cityMasterService.getByDistrictId(districtId);
			
			statusMap.put("cityMasterEntity",cityList);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	}
}
