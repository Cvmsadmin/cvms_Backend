package org.ss.vendorapi.entity;

import java.io.Serializable;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor
@Entity
@Where(clause="ACTIVE=1")
@Table(name="features_master")
public class FeatureMasterEntity extends ParentEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String featureName;
	private String subMenu;
	private Long parent;
	private String url;
	

}
