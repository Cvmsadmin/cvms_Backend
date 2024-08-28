package org.ss.vendorapi.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.BaseLocationEntity;
import org.ss.vendorapi.service.BaseLocationService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class BaseLocation {
	
	
	 @Autowired
	    private BaseLocationService baseLocationService;

	
	@PostMapping("/addLocation")
    public ResponseEntity<?> addLocation(@RequestBody List<BaseLocationEntity> locationList, HttpServletRequest request) {
        Map<String, Object> statusMap = new HashMap<>();
        List<BaseLocationEntity> savedEntities = new ArrayList<>();

        try {
            for (BaseLocationEntity locationEntity : locationList) {
                if (locationEntity.getSrNo() == null || locationEntity.getLocation() == null) {
                    return new ResponseEntity<>("Parameters missing", HttpStatus.EXPECTATION_FAILED);
                }

                try {
                    BaseLocationEntity savedEntity = baseLocationService.save(locationEntity);
                    savedEntities.add(savedEntity);
                } catch (Exception ex) {
                    statusMap.put("statusMsg", "Error saving location");
                    statusMap.put("statusCode", "SAVE_ERROR");
                    statusMap.put("status", "FAIL");
                    return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            if (!savedEntities.isEmpty()) {
                statusMap.put("statusMsg", "Location(s) created successfully");
                statusMap.put("status", "SUCCESS");
                statusMap.put("statusCode", "LOC_200");
                return new ResponseEntity<>(statusMap, HttpStatus.OK);
            } else {
                statusMap.put("statusMsg", "No location created");
                statusMap.put("status", "FAIL");
                statusMap.put("statusCode", "LOC_301");
                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/allLocation")
    public ResponseEntity<List<BaseLocationEntity>> getAllLocations() {
        List<BaseLocationEntity> locations = baseLocationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
	
	
	
	

}
