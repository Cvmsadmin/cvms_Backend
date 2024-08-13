package org.ss.vendorapi.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ROLE_RESOURCE_MASTER")
public class RoleResourceMasterEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "RESOURCE_URL")
    private String resourceUrl;
    
    @Column(name = "RESOURCE_DESCRIPTION")
    private String resourceDescription;
    
    @Column(name = "ROLE_ID")
    private String roleId;
    
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    
    @Column(name = "ACTIVE")
    private Integer active;
    
}
