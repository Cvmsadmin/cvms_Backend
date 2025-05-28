
package org.ss.vendorapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "client_invoice_description_values")
@Getter
@Setter
public class ClientInvoiceDescriptionValue {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "client_invoice_id", referencedColumnName = "id", nullable = false)
	private ClientInvoiceMasterEntity clientInvoice;


    @Column(name = "invoice_no")
    private String invoiceNo;


    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    @Column(name = "item_description")
    private String itemDescription;
    
    @Column(name = "base_value")
    private String baseValue;
    
    @Column(name = "gst_per")
    private Double gstPer;
    
    @Column(name = "cgst")
    private Double cgst;
    
    @Column(name = "sgst")
    private Double sgst;
    
    @Column(name = "igst")
    private Double igst;
    
    @Column(name = "amt_incl_gst")
    private Double amtInclGst;


//	public void setClientInvoiceMaster(ClientInvoiceMasterEntity existingInvoice) {
//		// TODO Auto-generated method stub
//		
//	}

}
