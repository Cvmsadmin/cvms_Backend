package org.ss.vendorapi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.controller.ClientInvoiceMasterController;

@Service
public class InvoiceEmailSchedulerService {
	
	 @Autowired
	    private ClientInvoiceMasterController clientInvoiceMasterController;

	    @Async
	    public void scheduleInvoiceEmail(String invoiceNo, long delayMillis) {
	        try {
	            Thread.sleep(delayMillis);  // Delay for 30 sec (or 60 sec)
	            
	            // Construct request map as expected by the API
	            Map<String, String> requestMap = new HashMap<>();
	            requestMap.put("invoiceNo", invoiceNo);

	            // Trigger the email sending method
	            clientInvoiceMasterController.sendInvoiceEmailWithAttachment(requestMap);

	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

}
