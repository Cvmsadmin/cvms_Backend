package org.ss.vendorapi.util;

import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.GetRelatedConsumerDTO;
public class GetRelatedConsumerHelper {

	public GetRelatedConsumerDTO populateGetRelatedConsumerDTO(CustomerDetailsDTO  customerDetailsDTO) {
    	GetRelatedConsumerDTO getRelatedConsumerDTO = new GetRelatedConsumerDTO();	
    	getRelatedConsumerDTO.setConsumerName(customerDetailsDTO.getName());
    	getRelatedConsumerDTO.setkNumber(customerDetailsDTO.getPersonId());
    	return getRelatedConsumerDTO;
    }	
}