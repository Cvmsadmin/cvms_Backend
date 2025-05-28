package org.ss.vendorapi.entity;
import org.hibernate.annotations.Where;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Where(clause="ACTIVE=1")
@Table(name = "milestone_category")
public class MilestoneCategory {

private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long projectId;
	private String projectName;
	private String partition;
    private Long partitionAmount;
    private Double partitionPer;

}
