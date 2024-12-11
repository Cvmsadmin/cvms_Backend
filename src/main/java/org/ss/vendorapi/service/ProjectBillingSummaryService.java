package org.ss.vendorapi.service;



import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ProjectBillingSummary;
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
	
	@Autowired
    private EntityManagerFactory entityManagerFactory;
	
	@Autowired
    private CommonAppServiceImpl commonAppServiceImpl;

    @Autowired
    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;

    @Autowired
    private ProjectMasterRepository projectMasterRepository;

    public List<ProjectBillingSummary> calculateProjectBillingData() {
        // Fetch all project billing summaries
        List<ProjectBillingSummary> billingSummaries = projectBillingSummaryRepository.findAll();
        

        // Iterate through and populate data from related entities
        return billingSummaries.stream().map(summary -> {
            // Fetch project data based on projectName
            ProjectMasterEntity project = projectMasterRepository.findByProjectName(summary.getProjectName());

            if (project != null) {
                // Set the total billable amount from ProjectMasterEntity
                summary.setContractPrice(project.getContractPrice());  // From ProjectMasterEntity
            }

            // Fetch invoice data based on some relationship (e.g., clientId or invoice number)
            ClientInvoiceMasterEntity invoice = clientInvoiceMasterRepository.findByClientId(summary.getSrNo()); // Custom lookup logic can be added

            if (invoice != null) {
                // Set the total received amount from ClientInvoiceMasterEntity
                summary.setTotalReceivedAmount(invoice.getTotalPaymentReceived());  // From ClientInvoiceMasterEntity
            }

            // Calculate balance to be billed (custom logic)
            double totalBillableAmount = Double.parseDouble(summary.getContractPrice());
            double totalReceivedAmount = Double.parseDouble(summary.getTotalReceivedAmount());
            summary.setBalanceToBeBilled(String.valueOf(totalBillableAmount - totalReceivedAmount));

            return summary;
        }).collect(Collectors.toList());
    }
    
    public void getAllSummaryData() {
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
//    		log.log(Level.INFO, "Pending Transactions List Size : " + resultList.size());      
    		Object[] objArr;   
    		ResponseEntity<?> retTransRespObj;  
    		Map<String, Object> statusMap;   
    		   
    	      String projectId = null;
    	      
    		for(Object obj: resultList) {    
    			objArr = (Object[]) obj; 
    		if(UtilValidate.isNotEmpty(objArr)) {    
    			projectId = CommonUtils.checkForEmptyAndReturn(objArr[0], blank);  
    			System.out.println(projectId);
    			//discomId = CommonUtils.checkForEmptyAndReturn(objArr[1], blank);     
    			//pgErrorDetail = CommonUtils.checkForEmptyAndReturn(objArr[2], blank);   
    			orderId = CommonUtils.checkForEmptyAndReturn(objArr[3], blank);
    		}
    		}
    	}
    }
}


