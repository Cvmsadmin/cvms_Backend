package org.ss.vendorapi.modal;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MULTIPLE_ACCOUNTS_DETAILS")
public class MultiAccountEntity {

	@Id
	@Column(name = "track_id", nullable = false, updatable = false, unique = true)
	private Long trackId;
	@Column(name = "primary_account", nullable = false)
	private String primaryAccount;
	@Column(name = "secondry_account", nullable = false)
	private String secondaryAccount;
	private String discom;
	@Column(name = "creation_date", nullable = false)
	private Timestamp creationDate;
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	private String status;
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	
}