package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.InvoiceDescriptionValue;



public interface ClientInvoiceDescriptionValueService {
	
	ClientInvoiceDescriptionValue save(ClientInvoiceDescriptionValue clientinvoiceDescriptionValue);

//	Optional findByClientInvoice(ClientInvoiceMasterEntity invoice);
	
	 List<ClientInvoiceDescriptionValue> findByClientInvoice(ClientInvoiceMasterEntity clientInvoice);


}
