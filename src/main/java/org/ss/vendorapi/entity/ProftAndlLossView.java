package org.ss.vendorapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "proft_and_loss_view")
public class ProftAndlLossView {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID for JPA
    private Long id;
	
	
	private String client_name; 
	
	private String project_name	;
	private int project_id	;
	private String client_cost_excl_gst	;
	private String vendor_cost_excl_gst	;
	private String margin;	
	private String margin_percentage;

}
