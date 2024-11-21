package org.ss.vendorapi.entity;

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

//    @ManyToOne
//    @JoinColumn(name = "invoice_id", nullable = false)
	@ManyToOne
    @JoinColumn(name = "client_invoice_id", nullable = false)
    private ClientInvoiceMasterEntity ClientInvoice;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "base_value")
    private String baseValue;


}
