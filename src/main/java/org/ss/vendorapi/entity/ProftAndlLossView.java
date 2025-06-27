package org.ss.vendorapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;
import jakarta.persistence.*;



@Data
@Entity
@Table(name = "proft_and_loss_view")
public class ProftAndlLossView {
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID for JPA
//    private Long id;
//	
//	private String client_name; 
//	
//	private String project_name	;
//	private int project_id	;
//	private String client_cost_excl_gst	;
//	private String vendor_cost_excl_gst	;
//	private String margin;	
//	private String margin_percentage;
//
//	  // These come from float8 (double precision) in PostgreSQL
////    @Column(columnDefinition = "float8")
//    private Double realised_profit;
//
////    @Column(columnDefinition = "float8")
//    private Double unrealised_profit;
//
////    @Column(columnDefinition = "float8")
//    private Double penalty;
	

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String client_name;

	    private String project_name;

	    private Long project_id;

	    // Numeric values from PostgreSQL - use BigDecimal
	    private String client_cost_excl_gst;
	    private String vendor_cost_excl_gst;
	    private String margin;
	    private String margin_percentage;

	    // Float8 / double precision - can still use BigDecimal or Double
	    private String realised_profit;
	    private String unrealised_profit;
	    private String penalty;

	    private String account_manager_id;
	    private String project_manager_id;

	
}
