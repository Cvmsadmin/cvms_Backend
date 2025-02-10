package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;
import org.ss.vendorapi.repository.ClientInvoiceMasterRepository;
import org.ss.vendorapi.repository.ClientMasterRepository;

@Service
public class ClientInvoiceMasterServiceImpl implements ClientInvoiceMasterService {
    
    @Autowired 
    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;
    
    @Autowired
    private ClientMasterRepository clientMasterRepository;

    @Override
    public ClientInvoiceMasterEntity save(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
        clientInvoiceMasterEntity.setActive(1);
        clientInvoiceMasterEntity.setCreateDate(new java.sql.Date(new Date().getTime()));
        return clientInvoiceMasterRepository.save(clientInvoiceMasterEntity);
    }

    @Override
    public ClientInvoiceMasterEntity update(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
        clientInvoiceMasterEntity.setUpdateDate(new java.sql.Date(new Date().getTime()));
        return clientInvoiceMasterRepository.save(clientInvoiceMasterEntity);
    }

    @Override
    public List<ClientInvoiceMasterEntity> findAll() {
        return clientInvoiceMasterRepository.findAll();
    }

    @Override
    public ClientInvoiceMasterEntity findById(Long id) {
        return clientInvoiceMasterRepository.findById(id).orElse(null);
    }

//    @Override
//    public ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo) {
//        return clientInvoiceMasterRepository.findByInvoiceNo(invoiceNo);
//    }
    
    @Override
  public ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo) {
      return clientInvoiceMasterRepository.findByInvoiceNoNative(invoiceNo);
  }

    @Override
    public ClientMasterEntity findClientByClientName(String clientName) {
        return clientMasterRepository.findByClientName(clientName); 
    }
    
//    @Override
//    public ClientMasterEntity findClientByClientName(String clientName) {
//        Optional<ClientMasterEntity> clientOptional = clientMasterRepository.findByClientName(clientName);
//        return clientOptional.orElse(null);  // Return null if no client is found
//    }
    
//    public ReceivableInvoiceStatsDTO getReceivableInvoiceStats() {
//        return clientMasterRepository.getReceivableInvoiceStats();
//    }
    

}
