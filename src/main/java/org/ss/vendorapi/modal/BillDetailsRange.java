/**
 * This code contains copyright information which is the proprietary property
 * of Uttar Pradesh Power Corporation Ltd. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of Uttar
 * Pradesh Power Corporation Ltd.
 *
 * Copyright (C) Uttar Pradesh Power Corporation Ltd. 2023
 * All rights reserved.
 * 
 *@author Sony
 *
 */

package org.ss.vendorapi.modal;

public class BillDetailsRange {

    private String fromDate;

    private String toDate;

    private String duration;

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

}
