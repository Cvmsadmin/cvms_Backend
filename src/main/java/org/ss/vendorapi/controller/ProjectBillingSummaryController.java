package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ProjectPaymentSummary;
//import org.ss.vendorapi.modal.ProjectBillingSummaryDTO;
//import org.ss.vendorapi.service.CommonAppServiceImpl;
import org.ss.vendorapi.service.ProjectBillingSummaryService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin("*")
@RestController
@RequestMapping("v2/api")
public class ProjectBillingSummaryController {
	
	 @Autowired
	 private ProjectBillingSummaryService projectBillingSummaryService;
	 
//	 @Autowired
//	 private CommonAppServiceImpl commonAppServiceImpl;
	 
	 
//	 @GetMapping("/calculate")
//	    public List<ProjectPaymentMaster> getProjectBillingSummary() {
//	        return projectBillingSummaryService.calculateProjectBillingData();
//	    }
	
	 @GetMapping("/summary")
	    public ResponseEntity<List<ProjectPaymentSummary>> getProjectBillingSummary() {
	        List<ProjectPaymentSummary> summaryList = projectBillingSummaryService.getProjectBillingSummary();
	        return new ResponseEntity<>(summaryList, HttpStatus.OK);
	    }
	 
	 
	 
	 
	 
	 
	
//	 @GetMapping("/getdata")
//	   public void getdata( ) {
//	
//		 
//		 List<ProjectBillingSummaryDTO> listOfProjectBillingSummary = new ArrayList<>();
//			
//	
//		    	String blank = "", orderId = null, transId = blank; 
//		    	//, discomId = blank, pgErrorDetail;  
//		    	
////		    	String strQuery = "SELECT project_name, contract_price, total_payment_received, balance_to_be_billed, view_details FROM project_master pm INNER JOIN client_invoice_master cim ON pm.client_id = cim.client_name";
//		    	String strQuery = "SELECT pm.project_name, pm.contract_price, cim.total_payment_received "
//		                 + "FROM project_master pm "
//		                 + "INNER JOIN client_invoice_master cim ON pm.client_id = cim.client_name";
//		    
//		          List<Object> resultList = null;
//		          int contractPrice = 0;
//		          int totalPaymentReceived = 0;
//		          String viewDetails = null;
//		          String projectName = null;
//
//		// Execute the query
//		          int finaltotalPaymentReceived = 0;
//		          resultList = commonAppServiceImpl.executeNativeQuery(strQuery);
//
//		          if (UtilValidate.isNotEmpty(resultList)) {
//		        	  
//		             for (Object obj : resultList) {
//		                // Since GSCNO is a single column, it should be a String
//		                
//		                   Object[] objArr = (Object[]) obj; // Handle if needed
//		                   if (UtilValidate.isNotEmpty(objArr)) {
//		                	   
//		                	   contractPrice = Integer.parseInt((String) objArr[0]);
//		                	   totalPaymentReceived = Integer.parseInt((String) objArr[1]);
//		                	   
//		                	 finaltotalPaymentReceived = finaltotalPaymentReceived + totalPaymentReceived;
//		                		                	   
//		                   }
//		                }
//		             int balanceToBeBilled = contractPrice - finaltotalPaymentReceived ;
//		             System.out.println(balanceToBeBilled);
//		             
////		             Remaining balance into the table projectBillingSummary
//		             
//		             strQuery = "UPDATE project_billing_summary\r\n"
//		             		+ "SET \r\n"
//		             		+ "    CONTRACT_PRICE = (\r\n"
//		             		+ "        SELECT CAST(pm.CONTRACT_PRICE AS NUMERIC)\r\n"
//		             		+ "        FROM project_master pm\r\n"
//		             		+ "        WHERE pm.PROJECT_NAME = 'projectName'\r\n"
//		             		+ "        LIMIT 1\r\n"
//		             		+ "    ),\r\n"
//		             		+ "    total_received_amount = (\r\n"
//		             		+ "        SELECT SUM(CAST(cim.TOTAL_PAYMENT_RECEIVED AS NUMERIC))\r\n"
//		             		+ "        FROM client_invoice_master cim\r\n"
//		             		+ "        INNER JOIN project_master pm \r\n"
//		             		+ "            ON pm.client_id = cim.client_id\r\n"
//		             		+ "        WHERE pm.PROJECT_NAME = 'projectName'\r\n"
//		             		+ "    ),\r\n"
//		             		+ "    BALANCE_TO_BE_BILLED = (\r\n"
//		             		+ "        SELECT CAST(pm.CONTRACT_PRICE AS NUMERIC) - SUM(CAST(cim.TOTAL_PAYMENT_RECEIVED AS NUMERIC))\r\n"
//		             		+ "        FROM project_master pm\r\n"
//		             		+ "        INNER JOIN client_invoice_master cim \r\n"
//		             		+ "            ON pm.client_id = cim.client_id\r\n"
//		             		+ "        WHERE pm.PROJECT_NAME = 'projectName'\r\n"
//		             		+ "       group by  CAST(pm.CONTRACT_PRICE AS NUMERIC)\r\n"
//		             		+ "    )\r\n"
//		             		+ "WHERE \r\n"
//		             		+ "    PROJECT_NAME = :'projectName'";
//
//		            	      
//		            	int retVal = commonAppServiceImpl.updateQuery(strQuery);
//		            	 System.out.println(strQuery);
//		             		             
//		             }
		          

	 }
