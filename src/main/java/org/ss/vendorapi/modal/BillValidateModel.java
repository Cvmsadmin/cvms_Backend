package org.ss.vendorapi.modal;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillValidateModel  implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private String kno;
	private String billNo;
	private String discomName;
	private String sbmBillFlag;
	private String duration;

}
