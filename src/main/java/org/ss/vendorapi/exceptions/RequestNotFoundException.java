package org.ss.vendorapi.exceptions;

import java.io.IOException;

public class RequestNotFoundException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	public RequestNotFoundException(String message) {
	    super(String.format("%s", message));
	}
	

}
