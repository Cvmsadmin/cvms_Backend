package org.ss.vendorapi.security;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = System.getLogger("OncePerRequestFilter");

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Value("${spring.ss.apiKey}")
    private String apiKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (isPublicApi(request)) {
			
			String requestHeader = request.getHeader("Authorization");

			String appServiceKey = request.getHeader("appServiceKey");

			ResponseEntity<?> responseEntity = CommonUtils.validateAppServiceKey(appServiceKey, apiKey);

			/** IF API KEY IS INVALID/CORRUPT RETURN THE RESPONSE */
			
			if(!request.getRequestURI().contains("/v3/")) {
				if (responseEntity != null) {
					logger.log(Level.INFO, "@@@@ PUBLIC : Invalid Header Value !! @@@@");
					this.pubAPIExcepMsg(response, "Access Denied !!");
					return;
				}
			}
		} else {
			String requestHeader = request.getHeader("Authorization");

			logger.log(Level.INFO, " Header :  {}" + requestHeader);
			String username = null, token = null;
			if (requestHeader != null && requestHeader.startsWith("Bearer")) {
				// looking good
				token = requestHeader.substring(7);
				try {

					username = this.jwtHelper.getUsernameFromToken(token);

				} catch (IllegalArgumentException e) {
					logger.log(Level.INFO, "Illegal Argument while fetching the username !!");
					e.printStackTrace();
				} catch (ExpiredJwtException e) {

					logger.log(Level.INFO, "Token is expired !!");
					e.printStackTrace();
//					this.privAPIExcepMsg(response, "Token is expired !!");
//					return;
				} catch (MalformedJwtException e) {
					logger.log(Level.INFO, "Invalid Token !!");
					e.printStackTrace();
//					this.privAPIExcepMsg(response, "Invalid Token !!");
//					return;
				} catch (Exception e) {
					logger.log(Level.INFO, "Access Denied !!");
					e.printStackTrace();
//					this.privAPIExcepMsg(response, "Access Denied !!");
//					return;
				}

			} else {
				logger.log(Level.INFO, "@@@@ PRIVATE : Invalid Header Value !! @@@@");
//				this.privAPIExcepMsg(response, "Invalid Header Value !! ");
//				return;
			}

			//
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				// fetch user detail from username
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
				if (validateToken) {

					// set the authentication
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);

				} else {
					logger.log(Level.INFO, "Validation fails !!");
//					this.privAPIExcepMsg(response, "Access Denied !!");
//					return;
				}

			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean isPublicApi(HttpServletRequest request) {

		if (request.getRequestURI().contains("/v2/") || request.getRequestURI().contains("Payment/") || request.getRequestURI().contains("/v3/")) {
			return true;
		}
		return false;
	}

	private void privAPIExcepMsg(HttpServletResponse response, String message) throws IOException {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final Map<String, Object> body = new HashMap<>();
		body.put(Parameters.statusMsg, message);
		body.put(Parameters.status, Constants.FAIL);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}

	private void pubAPIExcepMsg(HttpServletResponse response, String message) throws IOException {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		final Map<String, Object> body = new HashMap<>();
		body.put(Parameters.statusMsg, message);
		body.put(Parameters.status, Constants.FAIL);

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}

}
