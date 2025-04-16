package org.ss.vendorapi.controller;

import java.util.Collections;
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
import org.ss.vendorapi.entity.SqlExecutorEntity;
import org.ss.vendorapi.service.SqlExecutorService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/v2/api")
public class SqlExecutorController {

    @Autowired
    private SqlExecutorService sqlExecutorService;

    @PostMapping("/run")
    public ResponseEntity<?> runDynamicQuery(@RequestBody Map<String, String> request) {
        String reportName = request.get("reportName");
        String reportQuery = request.get("reportQuery");

        try {
            List<Map<String, Object>> result = sqlExecutorService.executeQueryAndSave(reportName, reportQuery);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    @GetMapping("/reports")
    public ResponseEntity<?> getAllReports() {
        try {
            List<SqlExecutorEntity> reports = sqlExecutorService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    @PostMapping("/runonly")
    public ResponseEntity<?> runDynamicQuery1(@RequestBody Map<String, String> request) {
    	String reportName = request.get("reportName");
        String reportQuery = request.get("reportQuery");

        try {
            List<Map<String, Object>> result = sqlExecutorService.executeQuery(reportName, reportQuery);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }


}
