package org.ss.vendorapi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
	
	 // Store OTPs with their expiration time
    private Map<String, String> otpStore = new ConcurrentHashMap<>();
    private Map<String, Long> otpExpiry = new HashMap<>();
    private static final long EXPIRATION_TIME = 2 * 60 * 1000; // 2 minutes

    // Generate a 6-digit OTP
    public String generateOtpForEmail(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + EXPIRATION_TIME);
        return otp;
    }

    // Validate OTP
    public boolean validateOtp(String email, String otp) {
        if (!otpStore.containsKey(email)) {
            return false;
        }

        // Check if OTP is expired
        long currentTime = System.currentTimeMillis();
        long expirationTime = otpExpiry.getOrDefault(email, 0L);
        if (currentTime > expirationTime) {
            otpStore.remove(email);
            otpExpiry.remove(email);
            return false;
        }

        return otp.equals(otpStore.get(email));
    }

    // Invalidate OTP after use
    public void invalidateOtp(String email) {
        otpStore.remove(email);
        otpExpiry.remove(email);
    }
}


