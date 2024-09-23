package org.ss.vendorapi.modal.response;

import java.util.List;

import org.ss.vendorapi.modal.FeatureDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
	
	private String id;
	private String accessToken;
	private String refreshToken;
	private String username;
	private final String tokenType = "Bearer";
	private String status;
	private List<FeatureDTO> urls;
	
	public JwtResponse(String accessToken, String refreshToken,String username) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.username = username;
	}

}
