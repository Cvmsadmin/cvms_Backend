package org.ss.vendorapi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;
import org.ss.vendorapi.modal.VendorInvoiceProjection;
import org.ss.vendorapi.repository.ClientMasterRepository;
import org.ss.vendorapi.repository.VendorInvoiceMasterRepository;

@Service
public class VendorInvoiceMasterServiceImpl implements VendorInvoiceMasterService{

	@Autowired 
	private VendorInvoiceMasterRepository vendorinviceMasterRepository;
	
	@Autowired
    private ClientMasterRepository clientMasterRepository; 

	@Override
	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceMasterEntity) {
		vendorInvoiceMasterEntity.setActive(1);
		vendorInvoiceMasterEntity.setCreateDate(new Date());
		return vendorinviceMasterRepository.save(vendorInvoiceMasterEntity);
	}

	@Override
	public List<VendorInvoiceMasterEntity> getAllVendorInvoices() {
		return vendorinviceMasterRepository.findAll();
	}

	@Override
	public VendorInvoiceMasterEntity update(VendorInvoiceMasterEntity vendorInvoiceMasterEntity) {
		vendorInvoiceMasterEntity.setUpdateDate(new Date());
		return vendorinviceMasterRepository.save(vendorInvoiceMasterEntity);
	}

	@Override
	public VendorInvoiceMasterEntity findById(Long id) {
		return vendorinviceMasterRepository.findById(id).orElse(null);
	}

	@Override
	public List<VendorInvoiceMasterEntity> findAll() {
	    return vendorinviceMasterRepository.findAll();
	}

	@Override
    public Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo) {
        return vendorinviceMasterRepository.findByInvoiceNo(invoiceNo);
    }

	@Override
    public ClientMasterEntity findClientById(Long clientId) {
        return clientMasterRepository.findById(clientId).orElse(null); // Handle if not found
    }
	
	public PayableInvoiceStatsDTO getPayableInvoiceStats() {
//        return vendorinviceMasterRepository.getPayableInvoiceStats();
		return null;
    }
	
	
	@Override
	public Double getVendorAmountExcluGstByProjectName(String projectName) {
	    return vendorinviceMasterRepository.getVendorAmountExcluGstByProjectName(projectName);
	}

//	 @Override
//	    public Double getVendorAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate) {
//	        return vendorinviceMasterRepository.getSumByProjectNameAndDate(projectName, startDate, endDate);
//	    }


	 @Override
	    public Double getVendorAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate) {
	        try {
	            Double sum = vendorinviceMasterRepository.getVendorAmountExcluGstByProjectNameAndDate(projectName, startDate, endDate);
	            return (sum != null) ? sum : 0.0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0.0;
	        }
	    }

	 @Override
	    public List<VendorInvoiceMasterEntity> findByProjectName(String projectName) {
	        return vendorinviceMasterRepository.findByProjectName(projectName);
	    }

//	 @Override
//	 public List<VendorInvoiceProjection> getVendorInvoicesByManagerId(Long managerId) {
//	     List<Object[]> resultList = vendorinviceMasterRepository.findVendorInvoicesByManagerId(managerId);
//	     List<VendorInvoiceProjection> dtoList = new ArrayList<>();
//
//	     for (Object[] row : resultList) {
//	         VendorInvoiceProjection dto = new VendorInvoiceProjection();
//	         dto.setId(toString(row[0]));
//	         dto.setVendorName(toString(row[1]));
//	         dto.setClientName(toString(row[2]));
//	         dto.setProjectName(toString(row[3]));
//	         dto.setInvoiceNo(toString(row[4]));
//	         dto.setPoNo(toString(row[5]));
//	         dto.setInvoiceDate(toString(row[6]));
//	         dto.setInvoiceDueDate(toString(row[7]));
//	         dto.setInvoiceDescription(toString(row[8]));
//	         dto.setStatus(toString(row[9]));
//	         dto.setInvoiceAmountIncluGst(toString(row[10]));
//	         dto.setInvoiceAmtIncluGst(toString(row[11]));
//	         dto.setInvoiceBaseValue(toString(row[12]));
//	         dto.setGstBaseValue(toString(row[13]));
//	         dto.setInvoiceInclusiveOfGst(toString(row[14]));
//	         dto.setTdsBaseValue(toString(row[15]));
//	         dto.setTdsPer(toString(row[16]));
//	         dto.setTdsOnGst(toString(row[17]));
//	         dto.setIgstOnTds(toString(row[18]));
//	         dto.setCgstOnTds(toString(row[19]));
//	         dto.setSgstOnTds(toString(row[20]));
//	         dto.setTotalTdsDeducted(toString(row[21]));
//	         dto.setBalance(toString(row[22]));
//	         dto.setPenalty(toString(row[23]));
//	         dto.setPenaltyDeductionOnBase(toString(row[24]));
//	         dto.setGstOnPenalty(toString(row[25]));
//	         dto.setTotalPenaltyDeduction(toString(row[26]));
//	         dto.setCreditNote(toString(row[27]));
//	         dto.setTotalPaymentReceived(toString(row[28]));
//	         dto.setPaymentDate(toString(row[29]));
//	         dto.setTotalCgst(toString(row[30]));
//	         dto.setTotalSgst(toString(row[31]));
//	         dto.setTotalIgst(toString(row[32]));
//	         dto.setAmountExcluGst(toString(row[33]));
//
//	         dtoList.add(dto);
//	     }
//
//	     return dtoList;
//	 }
//
//	 // Helper
//	 private String toString(Object obj) {
//	     return obj != null ? obj.toString() : null;
//	 }
	 
	 @Override
	 public List<VendorInvoiceProjection> getVendorInvoicesByManagerId(Long managerId) {
	     List<Object[]> resultList = vendorinviceMasterRepository.findVendorInvoicesByManagerId(managerId);
	     List<VendorInvoiceProjection> dtoList = new ArrayList<>();

	     for (Object[] row : resultList) {
	         VendorInvoiceProjection dto = new VendorInvoiceProjection();
	         dto.setId(toString(row[0]));         
	         dto.setClientName(toString(row[1]));
	         dto.setClientId(toString(row[2]));
	         dto.setInvoiceAmountIncluGst(toString(row[3]));
	         dto.setInvoiceDate(toString(row[4]));
	         dto.setInvoiceDescription(toString(row[5]));
	         dto.setInvoiceDueDate(toString(row[6]));
	         dto.setInvoiceNo(toString(row[7]));
	         dto.setPenalty(toString(row[8]));
	         dto.setPoNo(toString(row[9]));
	         dto.setProjectName(toString(row[10]));
	         dto.setStatus(toString(row[11]));
	         dto.setVendorName(toString(row[13]));
	         dto.setBalance(toString(row[19]));
	         dto.setCgstOnTds(toString(row[20]));
	         dto.setGstBaseValue(toString(row[21]));
	         dto.setGstOnPenalty(toString(row[22]));
	         dto.setInvoiceBaseValue(toString(row[23]));
	         dto.setInvoiceInclusiveOfGst(toString(row[24]));
	         dto.setPenaltyDeductionOnBase(toString(row[25])); 
	         dto.setSgstOnTds(toString(row[26]));
	         dto.setTdsBaseValue(toString(row[27]));
	         dto.setTotalPaymentReceived(toString(row[28]));
	         dto.setTotalPenaltyDeduction(toString(row[29]));
	         dto.setTotalTdsDeducted(toString(row[30])); 
	         dto.setCreditNote(toString(row[31]));
	         dto.setIgstOnTds(toString(row[32]));
	         dto.setTdsPer(toString(row[33]));
	         dto.setTdsOnGst(toString(row[34]));
	         dto.setModeOfPayment(toString(row[37]));
	         dto.setTotalCgst(toString(row[38]));
	         dto.setTotalSgst(toString(row[39]));
	         dto.setTotalIgst(toString(row[40]));
	         dto.setAmountExcluGst(toString(row[41]));	         
	         dto.setInvoiceAmtIncluGst(toString(row[42]));
	         dto.setPaymentRequestSentDate(toString(row[43]));
	         dto.setBuApprovalDate(toString(row[44]));
	         dto.setSubmissionToFinanceDate(toString(row[45]));
	         dto.setPaymentDate(toString(row[46]));
	         dto.setPaymentAdviceNo(toString(row[47]));
	       
	         dtoList.add(dto);
	     }

	     return dtoList;
	 }

	 private String toString(Object obj) {
	     return obj != null ? obj.toString() : null;
	 }



}
