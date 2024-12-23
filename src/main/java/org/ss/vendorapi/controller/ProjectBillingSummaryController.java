package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ProjectBillingSummary;
import org.ss.vendorapi.service.CommonAppServiceImpl;
import org.ss.vendorapi.service.ProjectBillingSummaryService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin("*")
@RestController
@RequestMapping("v2/api")
public class ProjectBillingSummaryController {
	
	 @Autowired
	 private ProjectBillingSummaryService projectBillingSummaryService;
	 
	 @Autowired
	 private CommonAppServiceImpl commonAppServiceImpl;
	 
	 
	 @GetMapping("/calculate")
	    public List<ProjectBillingSummary> getProjectBillingSummary() {
	        return projectBillingSummaryService.calculateProjectBillingData();
	    }
	
	 @GetMapping("/getdata")
	   public void getdata( ) {
	
		 
		 List<ProjectBillingSummary> listOfProjectBillingSummary = new ArrayList<>();
			
	
		    	String blank = "", orderId = null, transId = blank; 
		    	//, discomId = blank, pgErrorDetail;  
		    	String strQuery = "SELECT \r\n"
		    			+ "    pm.id AS project_id,\r\n"
		    			+ "    pm.client_id AS project_client_id,\r\n"
		    			+ "    pm.project_name,\r\n"
		    			+ "    pm.address,\r\n"
		    			+ "    pm.city,\r\n"
		    			+ "    pm.state,\r\n"
		    			+ "    pm.contact_person,\r\n"
		    			+ "    pm.contact_no,\r\n"
		    			+ "    pm.email,\r\n"
		    			+ "    pm.contract_price,\r\n"
		    			+ "    cim.id AS invoice_id,\r\n"
		    			+ "    cim.client_name AS invoice_client_name,\r\n"
		    			+ "    cim.invoice_no,\r\n"
		    			+ "    cim.invoice_date,\r\n"
		    			+ "    cim.invoice_description,\r\n"
		    			+ "    cim.invoice_amount_inclu_gst,\r\n"
		    			+ "    cim.status\r\n"
		    			+ "FROM \r\n"
		    			+ "    project_master pm\r\n"
		    			+ "INNER JOIN \r\n"
		    			+ "    client_invoice_master cim\r\n"
		    			+ "ON \r\n"
		    			+ "    pm.project_name = cim.project_name\r\n"
		    			+ "WHERE \r\n"
		    			+ "    pm.active = 1 \r\n"
		    			+ "    AND cim.active = 1\r\n"
		    			+ "GROUP BY \r\n"
		    			+ "    pm.project_name, pm.id, pm.client_id, pm.address, pm.city, pm.state, \r\n"
		    			+ "    pm.contact_person, pm.contact_no, pm.email, pm.contract_price, \r\n"
		    			+ "    cim.id, cim.client_name, cim.invoice_no, cim.invoice_date, \r\n"
		    			+ "    cim.invoice_description, cim.invoice_amount_inclu_gst, cim.status";
		    			    
		    	List<Object> resultList = null;  
		    	try {   resultList = commonAppServiceImpl.findByNativeQuery(strQuery);  
		    	}  
		    	catch(Exception e) {   e.printStackTrace();  }    
		    	// Fetching payment gateway records based on filter - End    
		    	if(UtilValidate.isNotEmpty(resultList)) {   
//		    		log.log(Level.INFO, "Pending Transactions List Size : " + resultList.size());      
		    		Object[] objArr;   
		    		ResponseEntity<?> retTransRespObj;  
		    		Map<String, Object> statusMap;   
//		    		 String projectId, projectClientId, projectName, address, city, state, contactPerson, contactNo, email, contractPrice, 
//		               invoiceId, invoiceClientName, invoiceNo, invoiceDate, invoiceDescription, invoiceAmountIncluGst, status;
		       	    	     
		    		for(Object obj: resultList) {    
		    			objArr = (Object[]) obj; 
		    		if(UtilValidate.isNotEmpty(objArr)) {   
//		    			listOfProjectBillingSummary.add(ProjectBillingSummary.builder()
//		                        .Id(CommonUtils.checkForEmptyAndReturn(objArr[0], blank))
//		                        .clientId(CommonUtils.checkForEmptyAndReturn(objArr[1], blank))
//		                        .projectName(CommonUtils.checkForEmptyAndReturn(objArr[2], blank))
//		                        .address(CommonUtils.checkForEmptyAndReturn(objArr[3], blank))
//		                        .city(CommonUtils.checkForEmptyAndReturn(objArr[4], blank))
//		                        .state(CommonUtils.checkForEmptyAndReturn(objArr[5], blank))
//		                        .contactPerson(CommonUtils.checkForEmptyAndReturn(objArr[6], blank))
//		                        .contactNo(CommonUtils.checkForEmptyAndReturn(objArr[7], blank))
//		                        .email(CommonUtils.checkForEmptyAndReturn(objArr[8], blank))
//		                        .contractPrice(CommonUtils.checkForEmptyAndReturn(objArr[9], blank))
//		                        .invoiceId(CommonUtils.checkForEmptyAndReturn(objArr[10], blank))
//		                        .invoiceClientName(CommonUtils.checkForEmptyAndReturn(objArr[11], blank))
//		                        .invoiceNo(CommonUtils.checkForEmptyAndReturn(objArr[12], blank))
//		                        .invoiceDate(CommonUtils.checkForEmptyAndReturn(objArr[13], blank))
//		                        .invoiceDescription(CommonUtils.checkForEmptyAndReturn(objArr[14], blank))
//		                        .invoiceAmountIncluGst(CommonUtils.checkForEmptyAndReturn(objArr[15], blank))
//		                        .status(CommonUtils.checkForEmptyAndReturn(objArr[16], blank))
//		                        .build());
		    		
//		    			listOfProjectBillingSummary.add(ProjectBillingSummary.builder().id(CommonUtils.checkForEmptyAndReturn(objArr[0], blank))..build());
		    			
//		    			//discomId = CommonUtils.checkForEmptyAndReturn(objArr[1], blank);     
//		    			//pgErrorDetail = CommonUtils.checkForEmptyAndReturn(objArr[2], blank);   
//		    			orderId = CommonUtils.checkForEmptyAndReturn(objArr[3], blank);
		    		}
	 
		    		}
		    	}
	 }}
