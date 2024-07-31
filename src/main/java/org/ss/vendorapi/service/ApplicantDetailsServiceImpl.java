//package org.ss.vendorapi.service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.ss.vendorapi.entity.ApplicantCommunicationAddress;
//import org.ss.vendorapi.entity.ApplicantDetailsEntity;
//import org.ss.vendorapi.modal.ApplicantDetailsModal;
//import org.ss.vendorapi.repository.ApplicantDetailsRepository;
//import org.ss.vendorapi.util.Constants;
//import org.ss.vendorapi.util.Parameters;
//
//@Service
//public class ApplicantDetailsServiceImpl implements ApplicantDetailsService {
//	
//	@Autowired
//	ApplicantDetailsRepository applicantDetailsRepository;
//
//	@Override
//	public ResponseEntity<?> saveApplicantDetails(ApplicantDetailsModal applicantDetails) {
//        Map<String, Object>	statusMap=new HashMap<>();
//		try 
//		{
//			ApplicantDetailsEntity applicantDetailsEntity=new ApplicantDetailsEntity();
//			applicantDetailsEntity.setPurposeOfSupply(applicantDetails.getPurposeOfSupply());
//			applicantDetailsEntity.setRequiredLoad(applicantDetails.getRequiredLoad());
//			applicantDetailsEntity.setDistrict(applicantDetails.getDistrict());
//			applicantDetailsEntity.setDivison(applicantDetails.getDivison());
//			applicantDetailsEntity.setName(applicantDetails.getName());
//			applicantDetailsEntity.setFatherOrHusbandName(applicantDetails.getFatherOrHusbandName());
//			applicantDetailsEntity.setOccupation(applicantDetails.getOccupation());
//			applicantDetailsEntity.setMotherName(applicantDetails.getMotherName());
//			
//			ApplicantCommunicationAddress applicantCommunicationAddress=new ApplicantCommunicationAddress();
//			applicantCommunicationAddress.setC_houseNo(applicantDetails.getC_houseNo());
//			applicantCommunicationAddress.setC_Building_colony(applicantDetails.getC_Building_colony());
//			applicantCommunicationAddress.setC_area(applicantDetails.getC_area());
//			applicantCommunicationAddress.setC_mobileNo(applicantDetails.getC_mobileNo());
//			applicantCommunicationAddress.setC_pinCode(applicantDetails.getC_pinCode());
//			applicantDetailsEntity.setApplicantCommunicationAddress(applicantCommunicationAddress);
//			applicantDetailsRepository.save(applicantDetailsEntity);
//			statusMap.put(Parameters.statusCode, "AD_100");
//			statusMap.put(Parameters.status, Constants.SUCCESS);
//			statusMap.put(Parameters.statusMsg,"Applicant Details save Successfully."+applicantDetailsEntity);
//			return new ResponseEntity<>(statusMap, HttpStatus.OK);
//			
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		statusMap.put(Parameters.status, Constants.FAIL);
//		return new ResponseEntity<>(statusMap,HttpStatus.BAD_REQUEST);
//	}
//
//}
