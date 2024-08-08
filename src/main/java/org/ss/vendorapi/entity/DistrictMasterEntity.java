package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="district_master")
@Entity
public class DistrictMasterEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="district_name")
    private String districtName;
    
    private String stateId;
    
    private Integer active;
    @Column(name="create_date")
	private Date 	createDate;
	
	@Column(name="update_date")
	private Date updateDate;
}
