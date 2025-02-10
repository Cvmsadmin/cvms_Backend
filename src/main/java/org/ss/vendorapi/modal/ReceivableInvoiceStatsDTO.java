package org.ss.vendorapi.modal;

import java.math.BigDecimal;

public class ReceivableInvoiceStatsDTO {
    private long receivableInvoiceCount;
    private BigDecimal receivableInvoiceAmount;

    public ReceivableInvoiceStatsDTO(long receivableInvoiceCount, BigDecimal receivableInvoiceAmount) {
        this.receivableInvoiceCount = receivableInvoiceCount;
        this.receivableInvoiceAmount = receivableInvoiceAmount;
        
    }

    // Getters and Setters
    public Long getReceivableInvoiceCount() {
        return receivableInvoiceCount;
    }

    public void setReceivableInvoiceCount(Long receivableInvoiceCount) {
        this.receivableInvoiceCount = receivableInvoiceCount;
    }

    public BigDecimal getReceivableInvoiceAmount() {
        return receivableInvoiceAmount;
    }

    public void setReceivableInvoiceAmount(BigDecimal receivableInvoiceAmount) {
        this.receivableInvoiceAmount = receivableInvoiceAmount;
    }

}
