package org.ss.vendorapi.advice;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EncryptedResponse<T> {

	private String _cdata;
}
