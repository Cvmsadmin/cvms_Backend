package org.ss.vendorapi.wrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class MultiReadHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private  byte[] requestBody;

    public MultiReadHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);

        try {
            InputStream inputStream = request.getInputStream();
            requestBody = inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error reading request body", e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        return new BufferedServletInputStream(new ByteArrayInputStream(requestBody));
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
    
    public void setRequestBody(String requestbody) {
    	try {
    		InputStream inputStream = new ByteArrayInputStream(requestbody.getBytes(StandardCharsets.UTF_8));
            requestBody = inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error reading request body", e);
        }
    }
    
}