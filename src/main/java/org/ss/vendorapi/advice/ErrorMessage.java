package org.ss.vendorapi.advice;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
}
