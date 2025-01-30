package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Where(clause = "ACTIVE=1")
@Table(name = "features_master")
public class FeatureMasterEntity extends ParentEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure the correct strategy (e.g., IDENTITY, SEQUENCE)
	private Long id;

	@Column(name = "feature_name", nullable = false)
	private String featureName;

	@Column(name = "sub_menu", nullable = false)
	private String subMenu;

	@Column(name = "parent")
	private Long parent;

	@Column(name = "url", nullable = false)
	private String url;

}
