package org.ss.vendorapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminTaskController 
{
	
	@PostMapping("/v2/api/acountCreation")
	public String accountCreation() 
	{
		return "account created";
	}

	
}
