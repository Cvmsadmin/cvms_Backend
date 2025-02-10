package org.ss.vendorapi.modal;

import java.math.BigDecimal;

public class PayableInvoiceStatsDTO {

	private Long payableInvoiceCount;
    private BigDecimal payableInvoiceAmount;

    public PayableInvoiceStatsDTO(Long payableInvoiceCount, BigDecimal payableInvoiceAmount) {
        this.payableInvoiceCount = payableInvoiceCount;
        this.payableInvoiceAmount = payableInvoiceAmount;
    }

    // Getters and Setters
    public Long getPayableInvoiceCount() {
        return payableInvoiceCount;
    }

    public void setPayableInvoiceCount(Long payableInvoiceCount) {
        this.payableInvoiceCount = payableInvoiceCount;
    }

    public BigDecimal getPayableInvoiceAmount() {
        return payableInvoiceAmount;
    }

    public void setPayableInvoiceAmount(BigDecimal payableInvoiceAmount) {
        this.payableInvoiceAmount = payableInvoiceAmount;
    }
	
}
