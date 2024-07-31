 /**
 * This code contains copyright information which is the proprietary property
 * of Uttar Pradesh Power Corporation Ltd. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of Uttar
 * Pradesh Power Corporation Ltd.
 *
 * Copyright (C) Uttar Pradesh Power Corporation Ltd. 2023
 * All rights reserved.
 * 
 *@author Sonee.Gupta
 */
package org.ss.vendorapi.util;

import java.util.Random;

/**
 * This class is Generating New Password on Random basis.Which includes A combination of 0-9a-zA-Z
 * and Special Characters they are !@#$%^&* and the length would be as per passed argument.
 * 
 * 
 */
public class RandomPasswordUtil {
    private static final char[] chars = new char[52];

    private static final char[] nums = new char[10];
    static {
        for (int idx = 0; idx < 10; ++idx)
            nums[idx] = (char) ('0' + idx);
        for (int idx = 0; idx < 26; ++idx)
            chars[idx] = (char) ('a' + idx);
        for (int idx = 26; idx < 52; ++idx)
            chars[idx] = (char) ('A' + idx - 26);
    }

    private final Random random = new Random();

    private final char[] buf;

    public RandomPasswordUtil(int length) {
        if (length < 1)
            throw new IllegalArgumentException("length < 1: " + length);
        buf = new char[length];
    }

    /**
     * This method is generating New Password.
     * 
     * @return
     */
    public String getNewPassword() {
        char spl[] = { '!', '@', '#', '$', '%', '^', '&', '*' };
        boolean pwdToggle = true;

        // String pswd = spl[random.nextInt(8)] + new String(buf);
        for (int idx = 0; idx < buf.length; ++idx) {
            if (idx == 2)
                buf[idx] = spl[random.nextInt(8)];
            else {
                if (pwdToggle) {
                    buf[idx] = chars[random.nextInt(chars.length)];
                    pwdToggle = false;
                } else {
                    buf[idx] = nums[random.nextInt(nums.length)];
                    pwdToggle = true;
                }
            }
        }
        return new String(buf);
        // return EncryptUtil.Encryptor.encrypt("46200B5ECB3B2C5B2F07977FAE40DC3746200B5ECB3B2C5B",
        // "F2B6E7AA3201D383",pswd);
    }

    /*
     * public static void main(String str[]) {
     * System.out.println("Hello");
     * for (int i = 0; i < 5; i++) {
     * RandomPassword rs = new RandomPassword(8);
     * System.out.println(rs.getNewPassword());
     * }
     * }
     */

}
