package org.ss.vendorapi.service;

import org.springframework.core.env.Environment;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.CustomerDetailsModel;


public interface ConsumerDataAccessorService {

    public CustomerDetailsDTO getCustomerDetails(String kno,String serviceName,String discomName,Environment env) throws RequestNotFoundException;
    public String updateUserDetails(CustomerDetailsModel custDetails,String serviceName,String discomName,Environment env) throws RequestNotFoundException;
}
