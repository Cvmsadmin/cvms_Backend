package org.ss.vendorapi.modal;

import java.util.List;

import lombok.Data;

@Data
public class RoleResourceDTO {

	private List<String> features;
	private String roleId;
}
