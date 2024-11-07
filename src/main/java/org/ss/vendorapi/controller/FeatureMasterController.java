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
import org.ss.vendorapi.entity.FeatureMasterEntity;
import org.ss.vendorapi.service.FeatureMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class FeatureMasterController {

	@Autowired
	private FeatureMasterService featureMasterService;


	@EncryptResponse
	@PostMapping("/saveFeature")
	public ResponseEntity<?> roleFeature(@RequestBody FeatureMasterEntity roleFeatureMaster){
		Map<String, Object> statusMap=new HashMap<String, Object>();
		try {

			if(UtilValidate.isEmpty(roleFeatureMaster.getFeatureName())){
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}
			if(roleFeatureMaster.getSubMenu().equals("N")) {
				roleFeatureMaster.setParent(-1l);
			}
			roleFeatureMaster=featureMasterService.save(roleFeatureMaster);

			statusMap.put("RoleFeatureMaster",roleFeatureMaster);
			statusMap.put("status", "SUCCESS");
			statusMap.put("statusCode", "RME_200");
			statusMap.put("statusMessage", "SUCCESSFULLY SAVED"); 
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			statusMap.put("status", "FAILED");
			statusMap.put("statusCode", "RME_301");
			statusMap.put("statusMessage", "FAILED TO CREATD"); 
			return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
		}
	}

	@EncryptResponse
	@GetMapping("/getMainFeatures")
	public ResponseEntity<?> getRoleFeature(){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {
			List<FeatureMasterEntity> roleFeatureList=featureMasterService.getMainFeatures();

			statusMap.put("mainFatures",roleFeatureList);
			statusMap.put(Parameters.status, "Success");
			statusMap.put(Parameters.statusCode, "RME_200");
			statusMap.put(Parameters.statusMsg,"SuccessFully Found");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		statusMap.put(Parameters.status, "ERROR");
		statusMap.put(Parameters.statusCode, "RME_400");
		statusMap.put(Parameters.statusMsg,"Not found");
		return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
	}

	
	@EncryptResponse
	@GetMapping("/getSubFeatures")
	public ResponseEntity<?> getSubFeatures( @RequestParam("id") String id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {
			List<FeatureMasterEntity> roleFeatureList=featureMasterService.findByWhere("o.parent="+id);

			statusMap.put("subFeatures",roleFeatureList);
			statusMap.put(Parameters.status, "Success");
			statusMap.put(Parameters.statusCode, "RME_200");
			statusMap.put(Parameters.statusMsg,"SuccessFully Found");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		statusMap.put(Parameters.status, "ERROR");
		statusMap.put(Parameters.statusCode, "RME_400");
		statusMap.put(Parameters.statusMsg,"Not found");
		return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
	}
}	