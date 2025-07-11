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

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Entity
@Where(clause = "ACTIVE = 1")
@Table(name = "client_master")
public class ClientMasterEntity extends ParentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "district")
    private String district;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "gst")
    private String gst;

    @Column(name = "pan")
    private String pan;

    @Column(name = "type_of_service")
    private String typeOfService;

    @Column(name = "account_managerId")
    private String accountManagerId;
    
    @Column(name = "alternate_number")
    private String alternateNumber;

}
