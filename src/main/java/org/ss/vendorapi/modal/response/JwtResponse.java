package org.ss.vendorapi.modal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
	
	private String accessToken;
	private String refreshToken;
	private String username;
	private final String tokenType = "Bearer";
	private String status;
	
	public JwtResponse(String accessToken, String refreshToken,String username) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.username = username;
	}

}
