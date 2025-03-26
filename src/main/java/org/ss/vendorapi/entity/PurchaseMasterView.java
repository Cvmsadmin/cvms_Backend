package org.ss.vendorapi.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Purchase_master_view")
public class PurchaseMasterView {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String client_id; 
	private String description;

	private String purchase_order_no; 
	private String purchase_register_amount; 
	private Date purchase_register_date; 
	private String purchase_register_no; 
	private String project_name; 
	
	private String requestor_name; 
	private String status;
	private String vendor; 
	private String billability; 
	private String pr_for; 
	private String rejection_reason; 
	private String type_of_expenditure;
	private Date approve_date;
	private Date po_approve_date; 
	private String vendor_name;
	private String client_name; 
	private String vendor_id; 
	private Date start_date; 
	private Date end_date; 

}
