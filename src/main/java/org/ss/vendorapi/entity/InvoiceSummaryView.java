package org.ss.vendorapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "pending_payable_invoices")
public class InvoiceSummaryView {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	
	
    private String aging_bucket;
    private String total_invoices;
    private BigDecimal total_amount;
 

    public String getReceivable_invoice_count() {
        return total_invoices;
    }
    public void setReceivable_invoice_count(String total_invoices) {
        this.total_invoices = total_invoices;
    }
    public BigDecimal getReceivable_invoice_amount() {
        return total_amount;
    }
    public void setReceivable_invoice_amount(BigDecimal receivable_invoice_amount, BigDecimal total_amount) {
        this.total_amount = total_amount;
    }
    
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getAging_bucket() {
		return aging_bucket;
	}
	public void setAging_bucket(String aging_bucket) {
		this.aging_bucket = aging_bucket;
	}
	
}
