package org.ss.vendorapi.helper;

import java.util.Calendar;
import java.util.Date;

public class UtilityHelper {

    public String getDateTimeAsString() {
        Calendar a = Calendar.getInstance();
        StringBuffer dsb = new StringBuffer();
        dsb = dsb.append(a.get(Calendar.DATE) <= 9 ? "0" + a.get(Calendar.DATE) : a.get(Calendar.DATE));
        dsb = dsb.append((a.get(Calendar.MONTH) + 1) <= 9 ? "0" + (a.get(Calendar.MONTH) + 1)
                : a.get(Calendar.MONTH) + 1);
        dsb = dsb.append(a.get(Calendar.YEAR));
        dsb = dsb.append(a.get(Calendar.HOUR_OF_DAY) <= 9 ? "0" + a.get(Calendar.HOUR_OF_DAY) : a
                .get(Calendar.HOUR_OF_DAY));
        dsb = dsb.append(a.get(Calendar.MINUTE) <= 9 ? "0" + a.get(Calendar.MINUTE) : a.get(Calendar.MINUTE));
        dsb = dsb.append(a.get(Calendar.SECOND) <= 9 ? "0" + a.get(Calendar.SECOND) : a.get(Calendar.SECOND));
        return dsb.toString();

    }

    public String getDateTimeAsStringForHDFC_NB() {
        Calendar a = Calendar.getInstance();
        StringBuffer dsb = new StringBuffer();
        dsb = dsb.append(a.get(Calendar.DATE) <= 9 ? "0" + a.get(Calendar.DATE) : a.get(Calendar.DATE));
        dsb = dsb.append((a.get(Calendar.MONTH) + 1) <= 9 ? "0" + (a.get(Calendar.MONTH) + 1)
                : a.get(Calendar.MONTH) + 1);
        dsb = dsb.append(a.get(Calendar.HOUR_OF_DAY) <= 9 ? "0" + a.get(Calendar.HOUR_OF_DAY) : a
                .get(Calendar.HOUR_OF_DAY));
        dsb = dsb.append(a.get(Calendar.MINUTE) <= 9 ? "0" + a.get(Calendar.MINUTE) : a.get(Calendar.MINUTE));

        return dsb.toString();
    }
    
    public String getDateInString(Date date) {
    	Calendar a = Calendar.getInstance();
    	a.setTime(date);
    	StringBuffer dsb = new StringBuffer();
        dsb = dsb.append(a.get(Calendar.HOUR_OF_DAY) <= 9 ? "0" + a.get(Calendar.HOUR_OF_DAY) : a
                .get(Calendar.HOUR_OF_DAY));
        dsb = dsb.append(a.get(Calendar.MINUTE) <= 9 ? "0" + a.get(Calendar.MINUTE) : a.get(Calendar.MINUTE));
        dsb = dsb.append(a.get(Calendar.SECOND) <= 9 ? "0" + a.get(Calendar.SECOND) : a.get(Calendar.SECOND));
        return dsb.toString();
    }

}
