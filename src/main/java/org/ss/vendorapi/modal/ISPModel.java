package org.ss.vendorapi.modal;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
//@Table(schema = "cisadm",name="CM_ISPSERVICEDETAILS")
@Table(name="CM_ISPSERVICEDETAILS")
public class ISPModel {
	private String applicant_id;
	@Id
	private String request_id;
	private String service_code;
	private String account_number;
	private String discom;
	private String type_of_request;
	private String isp_update_status;
}