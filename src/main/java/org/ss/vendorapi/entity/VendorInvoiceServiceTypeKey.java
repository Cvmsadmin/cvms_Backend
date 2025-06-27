package org.ss.vendorapi.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class VendorInvoiceServiceTypeKey implements Serializable {
	
	 @Column(name = "vendor_invoice_id")
	 private Long vendorInvoiceId;

	    @Column(name = "service_id")
	    private Long serviceId;

	    public void setVendorInvoiceId(Long id) {
	        this.vendorInvoiceId = id;
	    }

	    public Long getVendorInvoiceId() {
	        return this.vendorInvoiceId;
	    }

	    public void setServiceId(Long id) {
	        this.serviceId = id;
	    }

	    public Long getServiceId() {
	        return this.serviceId;
	    }

}
