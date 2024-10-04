package org.ss.vendorapi.entity;

import java.io.Serializable;

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
@Table(name="city_master")
@Entity
public class CityMasterEntity extends ParentEntity implements Serializable{

	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	private String cityName;
	private String districtId;

}
