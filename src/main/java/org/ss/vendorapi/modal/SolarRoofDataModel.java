package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class SolarRoofDataModel {

	private String kno;
	private String discomName;
	private String solarRooftopCapacity;
	private String appliedNationalPortal;
	private String nationalPortalNumber;
	private String sanctionLoad;
	private String acceleratedBenefits;//Accelerated deprication benefits
	private String rooftopLocation;//location of proposed rooftop
	private String roofTopType;//Type of Rooftop
	private String charges;
	private String national_portal; //applied through national portal
	//Values from consumer Details start
	private String consumerName;
	private String email;
	private String category;
	//Values from consumer Details end
	private String netMeterChargesParam;
}