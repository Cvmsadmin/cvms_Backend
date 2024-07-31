package org.ss.vendorapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class TestController {
	
	@GetMapping("/v2/api/testing")
	public String testService() {
		return "WORKING FINE";
	}

}
