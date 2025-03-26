package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Entity
@Where(clause="ACTIVE=1")
@Table(name="vendor_master")
public class VendorMasterEntity extends ParentEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="vendor_name")
	private String vendorName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="pincode")
	private String pinCode;
	
	@Column(name="district")
	private String district;
	
	@Column(name="contact_person")
	private String contactPerson;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="email")
	private String email;
	
//	@ElementCollection
//	@Convert(converter = ListToStringConverter.class)
	@Column(name="type_of_service")
	private String typeOfService;

	
	@Column(name="gst")
	private String gst;
	
	@Column(name="pan_no")
	private String panNo;
	
	// Custom getter and setter to handle comma-separated values
    public List<String> getTypeOfService() {
        return typeOfService != null ? Arrays.asList(typeOfService.split(",")) : List.of();
    }

    public void setTypeOfService(List<String> typeOfService) {
        this.typeOfService = typeOfService != null ? String.join(",", typeOfService) : "";
    }

}
