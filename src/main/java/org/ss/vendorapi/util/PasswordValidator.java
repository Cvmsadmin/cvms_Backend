package org.ss.vendorapi.util;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 16;
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            return false;
        }

        if (!UPPER_CASE_PATTERN.matcher(password).find()) {
            return false;
        }

        if (!LOWER_CASE_PATTERN.matcher(password).find()) {
            return false;
        }

        if (!DIGIT_PATTERN.matcher(password).find()) {
            return false;
        }

        return true;
    }

}