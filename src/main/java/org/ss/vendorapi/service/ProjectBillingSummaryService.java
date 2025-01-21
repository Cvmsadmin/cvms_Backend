package org.ss.vendorapi.service;



import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ProjectPaymentSummary;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.repository.ClientInvoiceMasterRepository;
import org.ss.vendorapi.repository.ProjectBillingSummaryRepository;
import org.ss.vendorapi.repository.ProjectMasterRepository;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.persistence.EntityManagerFactory;

@Service
public class ProjectBillingSummaryService {
	
	@Autowired
    private ProjectBillingSummaryRepository projectBillingSummaryRepository;
	
	public List<ProjectPaymentSummary> getProjectBillingSummary() {
        return projectBillingSummaryRepository.findAll();
    }
	
	
//	@Autowired
//    private EntityManagerFactory entityManagerFactory;
//	
//	@Autowired
//    private CommonAppServiceImpl commonAppServiceImpl;
//
//    @Autowired
//    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;
//
//    @Autowired
//    private ProjectMasterRepository projectMasterRepository;
//
//    public List<ProjectPaymentMaster> calculateProjectBillingData() {
//        // Fetch all project billing summaries
//        List<ProjectPaymentMaster> billingSummaries = projectBillingSummaryRepository.findAll();
//        
//
//        // Iterate through and populate data from related entities
//        return billingSummaries.stream().map(summary -> {
//            // Fetch project data based on projectName
//            ProjectMasterEntity project = projectMasterRepository.findByProjectName(summary.getProjectName());
//
//            if (project != null) {
//                // Set the total billable amount from ProjectMasterEntity
//                summary.setContractPrice(project.getContractPrice());  // From ProjectMasterEntity
//            }
//
//            // Fetch invoice data based on some relationship (e.g., clientId or invoice number)
//            ClientInvoiceMasterEntity invoice = clientInvoiceMasterRepository.findByClientId(summary.getSrNo()); // Custom lookup logic can be added
//
//            if (invoice != null) {
//                // Set the total received amount from ClientInvoiceMasterEntity
//                summary.setTotalReceivedAmount(invoice.getTotalPaymentReceived());  // From ClientInvoiceMasterEntity
//            }
//
//            // Calculate balance to be billed (custom logic)
//            double totalBillableAmount = Double.parseDouble(summary.getContractPrice());
//            double totalReceivedAmount = Double.parseDouble(summary.getTotalReceivedAmount());
//            summary.setBalanceToBeBilled(String.valueOf(totalBillableAmount - totalReceivedAmount));
//
//            return summary;
//        }).collect(Collectors.toList());
//    }
//    
//    public void getAllSummaryData() {
//    	String blank = "", orderId = null, transId = blank; 
//    	//, discomId = blank, pgErrorDetail;  
//    	String strQuery = "SELECT \r\n"
//    			+ "    pm.id AS project_id,\r\n"
//    			+ "    pm.client_id AS project_client_id,\r\n"
//    			+ "    pm.project_name,\r\n"
//    			+ "    pm.address,\r\n"
//    			+ "    pm.city,\r\n"
//    			+ "    pm.state,\r\n"
//    			+ "    pm.contact_person,\r\n"
//    			+ "    pm.contact_no,\r\n"
//    			+ "    pm.email,\r\n"
//    			+ "    pm.contract_price,\r\n"
//    			+ "    cim.id AS invoice_id,\r\n"
//    			+ "    cim.client_name AS invoice_client_name,\r\n"
//    			+ "    cim.invoice_no,\r\n"
//    			+ "    cim.invoice_date,\r\n"
//    			+ "    cim.invoice_description,\r\n"
//    			+ "    cim.invoice_amount_inclu_gst,\r\n"
//    			+ "    cim.status\r\n"
//    			+ "FROM \r\n"
//    			+ "    project_master pm\r\n"
//    			+ "INNER JOIN \r\n"
//    			+ "    client_invoice_master cim\r\n"
//    			+ "ON \r\n"
//    			+ "    pm.project_name = cim.project_name\r\n"
//    			+ "WHERE \r\n"
//    			+ "    pm.active = 1 \r\n"
//    			+ "    AND cim.active = 1\r\n"
//    			+ "GROUP BY \r\n"
//    			+ "    pm.project_name, pm.id, pm.client_id, pm.address, pm.city, pm.state, \r\n"
//    			+ "    pm.contact_person, pm.contact_no, pm.email, pm.contract_price, \r\n"
//    			+ "    cim.id, cim.client_name, cim.invoice_no, cim.invoice_date, \r\n"
//    			+ "    cim.invoice_description, cim.invoice_amount_inclu_gst, cim.status";
//    			    
//    	List<Object> resultList = null;  
//    	try {   resultList = commonAppServiceImpl.findByNativeQuery(strQuery);  
//    	}  
//    	catch(Exception e) {   e.printStackTrace();  }    
//    	// Fetching payment gateway records based on filter - End    
//    	if(UtilValidate.isNotEmpty(resultList)) {   
////    		log.log(Level.INFO, "Pending Transactions List Size : " + resultList.size());      
//    		Object[] objArr;   
//    		ResponseEntity<?> retTransRespObj;  
//    		Map<String, Object> statusMap;   
//    		   
//    	      String projectId = null;
//    	      
//    		for(Object obj: resultList) {    
//    			objArr = (Object[]) obj; 
//    		if(UtilValidate.isNotEmpty(objArr)) {    
//    			projectId = CommonUtils.checkForEmptyAndReturn(objArr[0], blank);  
//    			System.out.println(projectId);
//    			//discomId = CommonUtils.checkForEmptyAndReturn(objArr[1], blank);     
//    			//pgErrorDetail = CommonUtils.checkForEmptyAndReturn(objArr[2], blank);   
//    			orderId = CommonUtils.checkForEmptyAndReturn(objArr[3], blank);
//    		}
//    		}
//    	}
//    }
  
	
//   88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888	
	
	
    
//    public List<ProjectBillingSummary> getProjectBillingSummary() {
//        List<ProjectBillingSummary> summaryList = new ArrayList<>();
//        String strQuery = "SELECT pm.project_name, pm.contract_price, SUM(CAST(cim.total_payment_received AS INTEGER)) AS total_payment_received " +
//                          "FROM project_master pm " +
//                          "LEFT JOIN client_invoice_master cim ON cim.project_name = pm.project_name " +
//                          "WHERE pm.ACTIVE = 1 " +
//                          "GROUP BY pm.project_name, pm.contract_price";
//
//        // Execute the query
//        List<Object[]> resultList = commonAppServiceImpl.executeNativeQuery(strQuery);
//
//        int finalTotalPaymentReceived = 0;
//
//        if (UtilValidate.isNotEmpty(resultList)) {
//            for (Object[] objArr : resultList) {
//                String projectName = (String) objArr[0];
//                
//                // Correct way to handle the contract_price field (cast Long to int)
//                Long contractPriceLong = (Long) objArr[1];  // Casting contract_price as Long
//                int contractPrice = contractPriceLong.intValue();  // Convert Long to int
//                
//                // Correct way to handle total_payment_received field (cast Long to int)
//                int totalPaymentReceived = objArr[2] != null ? Integer.parseInt(objArr[2].toString()) : 0;
//
//                finalTotalPaymentReceived += totalPaymentReceived;
//
//                // Calculate the balance to be billed
//                int balanceToBeBilled = contractPrice - finalTotalPaymentReceived;
//
//                // Create ProjectBillingSummary object and populate it
//                ProjectBillingSummary billingSummary = ProjectBillingSummary.builder()
//                        .projectName(projectName)
//                        .contractPrice(String.valueOf(contractPrice))  // Convert int to String if needed
//                        .totalReceivedAmount(String.valueOf(totalPaymentReceived))
//                        .balanceToBeBilled(String.valueOf(balanceToBeBilled))
//                        .build();
//
//                summaryList.add(billingSummary);
//            }
//        }
//
//        return summaryList;
//    }

//    public List<ProjectPaymentMaster> getProjectBillingSummary() {
//        List<ProjectPaymentMaster> summaryList = new ArrayList<>();
//        String strQuery = "SELECT pm.project_name, pm.contract_price, SUM(CAST(cim.total_payment_received AS INTEGER)) AS total_payment_received " +
//                          "FROM project_master pm " +
//                          "LEFT JOIN client_invoice_master cim ON cim.project_name = pm.project_name " +
//                          "WHERE pm.ACTIVE = 1 " +
//                          "GROUP BY pm.project_name, pm.contract_price";
//
//        // Execute the query
//        List<Object[]> resultList = commonAppServiceImpl.executeNativeQuery(strQuery);
//
//        int finalTotalPaymentReceived = 0;
//
//        if (UtilValidate.isNotEmpty(resultList)) {
//            for (Object[] objArr : resultList) {
//                String projectName = (String) objArr[0];
//                
//                // Correctly convert the contract_price field to Long
//                Long contractPriceLong = null;
//                if (objArr[1] != null) {
//                    try {
//                        contractPriceLong = Long.valueOf(objArr[1].toString());
//                    } catch (NumberFormatException e) {
//                        // Handle conversion failure
//                        contractPriceLong = 0L;  // Default to 0 if conversion fails
//                    }
//                } else {
//                    contractPriceLong = 0L;  // Default to 0 if the value is null
//                }
//
//                // Convert contractPriceLong to int
//                int contractPrice = contractPriceLong.intValue();
//                
//                // Handle total_received_amount safely
//                int totalPaymentReceived = objArr[2] != null ? Integer.parseInt(objArr[2].toString()) : 0;
//
//                finalTotalPaymentReceived += totalPaymentReceived;
//
//                // Calculate balance to be billed
//                int balanceToBeBilled = contractPrice - finalTotalPaymentReceived;
//
//                // Create ProjectBillingSummary object and populate it
//                ProjectPaymentMaster billingSummary = ProjectPaymentMaster.builder()
//                        .projectName(projectName)
//                        .contractPrice(String.valueOf(contractPrice))  // Convert int to String if needed
//                        .totalReceivedAmount(String.valueOf(totalPaymentReceived))
//                        .balanceToBeBilled(String.valueOf(balanceToBeBilled))
//                        .build();
//
//                summaryList.add(billingSummary);
//            }
//        }
//
//        return summaryList;
//    }    
    
}


