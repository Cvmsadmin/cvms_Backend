package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.repository.ClientInvoiceDescriptionValueRepository;

@Service
public class ClientInvoiceDescriptionValueServiceImpl implements ClientInvoiceDescriptionValueService {
	
	@Autowired 
	private ClientInvoiceDescriptionValueRepository clientInvoiceDescriptionValueRepository;

	public ClientInvoiceDescriptionValue save(ClientInvoiceDescriptionValue clientInvoiceDescriptionValue) {
        return clientInvoiceDescriptionValueRepository.save(clientInvoiceDescriptionValue);
    }


	@Override
    public List<ClientInvoiceDescriptionValue> findByClientInvoice(ClientInvoiceMasterEntity clientInvoice) {
        return clientInvoiceDescriptionValueRepository.findByClientInvoice_Id(new Long(1));
//		return null;
    }


	@Override
	public List<ClientInvoiceDescriptionValue> findByInvoiceNo(String invoiceNo) {
	
		return clientInvoiceDescriptionValueRepository.findByInvoiceNo(invoiceNo);
	}


}
