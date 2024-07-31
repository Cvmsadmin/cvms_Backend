package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class MultiAccountDTO {
	private String primaryAccount;
	private String secondaryAccount;
	private String discom;
}