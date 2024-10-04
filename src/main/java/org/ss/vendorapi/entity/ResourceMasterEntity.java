package org.ss.vendorapi.entity;

import java.io.Serializable;

import org.hibernate.annotations.Where;

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
@Where(clause="ACTIVE=1")
@Table(name="resource_master")
@Entity
public class ResourceMasterEntity  extends ParentEntity implements Serializable{


	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private String url;
	private String featureId;
	

}
