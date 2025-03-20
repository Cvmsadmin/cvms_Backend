package org.ss.vendorapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.InvoiceDescriptionValue;
import org.ss.vendorapi.repository.InvoiceDescriptionValueRepository;

@Service
public class InvoiceDescriptionValueServiceImpl implements InvoiceDescriptionValueService {
	
	@Autowired
    private InvoiceDescriptionValueRepository repository;

    @Override
    public InvoiceDescriptionValue save(InvoiceDescriptionValue DescriptionValue) {
        return repository.save(DescriptionValue);
    }

}
