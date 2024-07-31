package org.ss.vendorapi.service;

import org.springframework.core.env.Environment;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
import org.ss.vendorapi.modal.CustomerDetailsDTO;

public class CallAccessorService {
	public static CustomerDetailsDTO getConsumerResponseDTO(String kNo, String ServiceName,String discomName,Environment env)throws RequestNotFoundException{
		ConsumerDataAccessorServiceImpl consumerSOAAccessor = new ConsumerDataAccessorServiceImpl();
		CustomerDetailsDTO customerResDTO = new CustomerDetailsDTO();
		customerResDTO = consumerSOAAccessor.getCustomerDetails(kNo,ServiceName,discomName,env);
		return customerResDTO;
	}


}
