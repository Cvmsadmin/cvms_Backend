package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

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

@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Table(name="state_master")
@Entity
public class StateMasterEntity implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="state_name")
    private String stateName;
    
    private Integer active;
    
    @Column(name="create_date")
	private Date 	createDate;
	
	@Column(name="update_date")
	private Date updateDate;
}
    
	
