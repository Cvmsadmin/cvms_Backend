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
@Table(name="district_master")
@Where(clause="ACTIVE=1")
@Entity
public class DistrictMasterEntity extends ParentEntity implements Serializable {

	private static final long serialVersionUID=1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="district_name")
    private String districtName;
    
    private String stateId;
    
   
}
