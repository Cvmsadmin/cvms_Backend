package org.ss.vendorapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ApplicantPermanentAddress {
	
	@Column(name = "P_HOUSENO")
	private String p_houseNo;
	@Column(name = "P_BUILDING_COLONY")
	private String p_Building_colony;
	@Column(name = "P_AREA")
	private String p_area;
	@Column(name = "P_PINCODE")
	private String p_pinCode;
	@Column(name = "P_MOBILENO")
	private String p_mobileNo;

}