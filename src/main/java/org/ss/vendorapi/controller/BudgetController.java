package org.ss.vendorapi.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.modal.ProjectBudgetDTO;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class BudgetController {
	
	
    @PostMapping("/submit")
    public ResponseEntity<?> submitBudget(@RequestBody ProjectBudgetDTO budget) {
        // Save to DB or just log
        System.out.println("Received Budget: " + budget);
        return ResponseEntity.ok(Map.of(
            "status", "SUCCESS",
            "message", "Budget data saved successfully"
        ));
    }
	

}
