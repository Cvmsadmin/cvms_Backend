package org.ss.vendorapi.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ELECTRICITYTHEFTINFO")
public class ElectricityTheftModel implements Serializable{

private static final long serialVersionUID = 2L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(name = "ELEC_THEFT_SEQ", sequenceName = "ELEC_THEFT_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ELECTRICITYTHEFTINFO_SEQ")
	@SequenceGenerator(name = "ELECTRICITYTHEFTINFO_SEQ", allocationSize = 1)
	int ID;
	@Column(name="ENGAGED_NAME")
	String consumerEngagedName;
	@Column(name="ENGAGED_ADDRESS")
    String consumerEngagedAddr;
	@Column(name="ENGAGED_CRIME")
	String consumerEngagedCrime;
	@Column(name="INFORMER_NAME")
    String informerName;
	@Column(name="INFORMER_ADDRESS")
    String informerAddr;
	@Column(name="INFORMER_PHONE")
    String informerPhone;
}
