package org.ss.vendorapi.entity;

public class ErrorResponse {
	
	private String error;

    // Constructor to initialize the error message
    public ErrorResponse(String error) {
        this.error = error;
    }

    // Getter and Setter
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
