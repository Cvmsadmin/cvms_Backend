package org.ss.vendorapi.controller;

import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.authenticator.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.ProftAndlLossView;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.service.ClientMasterService;
import org.ss.vendorapi.service.ProftAndLossViewService;
import org.ss.vendorapi.service.ProjectMasterService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.RoleConstants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v2/api")
public class ProftAndLossViewController {
	
	@Autowired
    private ProftAndLossViewService service;
	
    @Autowired
    private ClientMasterService clientMasterService;
       
    @Autowired
    private UserMasterService userMasterService;
    
    @Autowired
    private ProjectMasterService projectMasterService;

    @GetMapping("/getProfitAndLossData")
    public ResponseEntity<?> getProfitAndLossData() {
        try {
            List<ProftAndlLossView> data = service.getAllProfitAndLossData();

            if (data.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Profit & Loss data found.");
            }

            return new ResponseEntity<>(data, HttpStatus.OK);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error while fetching Profit & Loss data: " + ex.getMessage());
        }
    }
    
    @GetMapping("/getProfitAndLossDataByMid/{managerId}")
    public ResponseEntity<?> getProfitAndLossDataByMid(@PathVariable Long managerId) {
        try {
            List<ProftAndlLossView> data = service.getProfitAndLossDataByMid(managerId);

            if (data.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No Profit & Loss data found for Manager ID: " + managerId);
            }

            return new ResponseEntity<>(data, HttpStatus.OK);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error while fetching data: " + ex.getMessage());
        }
    }

}
