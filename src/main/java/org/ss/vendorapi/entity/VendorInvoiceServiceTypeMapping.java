package org.ss.vendorapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendor_invoice_service_type_mapping")
public class VendorInvoiceServiceTypeMapping {
	
	@EmbeddedId
    private VendorInvoiceServiceTypeKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vendorInvoiceId")
    @JoinColumn(name = "vendor_invoice_id")
    private VendorInvoiceMasterEntity vendorInvoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private ServiceMasterEntity service;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "sr_no")
    private String srNo;

    public void setService(ServiceMasterEntity service) {
        this.service = service;
    }

    public void setVendorInvoice(VendorInvoiceMasterEntity vendorInvoiceMaster) {
        this.vendorInvoice = vendorInvoiceMaster;
    }

    public void setId(VendorInvoiceServiceTypeKey key) {
        this.id = key;
    }

    public void setServiceName(String serviceName2) {
        this.serviceName = serviceName2;
    }
    
    public String getServiceName() {
        return this.serviceName;
    }

    public void setSrNo(String srNo2) {
        this.srNo = srNo2;
    }
  
    public String getSrNo() {
        return this.srNo;
    }

    public ServiceMasterEntity getService() {
        return this.service;
    }


}
