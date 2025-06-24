package org.ss.vendorapi.security;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {
	
	public static Long getUserId(HttpServletRequest request) {
        // Extract userId from JWT or session
        return (Long) request.getAttribute("userId");
    }

    public static String getUsername(HttpServletRequest request) {
        return (String) request.getAttribute("username");
    }

}
