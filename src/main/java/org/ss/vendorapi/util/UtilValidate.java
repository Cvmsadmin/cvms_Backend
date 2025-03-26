package org.ss.vendorapi.util;

import java.util.Collection;
import java.util.Map;

public class UtilValidate {
 
	/** Check whether string s is NOT empty. */
    public static boolean isNotEmpty(String s) {
        return (s != null) && s.trim().length() > 0;
    }

    /** Check whether string s is NOT empty & not string null. */
    public static boolean isNotEmptyAndNotNull(String s) {
        return (s != null) && (s != "null") && s.trim().length() > 0;
    }
    
    /** Check whether string s is empty. */
    public static boolean isEmpty(String s) {
        return (s == null) || s.trim().length() == 0;
    }

    /** Check whether collection c is empty. */
    public static <E> boolean isEmpty(Collection<E> c) {
        return (c == null) || c.isEmpty();
    }

    /** Check whether map m is empty. */
    public static <K,E> boolean isEmpty(Map<K,E> m) {
        return (m == null) || m.isEmpty();
    }
    
    /** Check whether an object is NOT empty, will see if it is a String, Map, Collection, etc. */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }
    
    /** Check whether an object is empty, will see if it is a String, Map, Collection, etc. */
    @SuppressWarnings("unchecked")
	public static boolean isEmpty(Object value) {
        if (value == null) return true;

        if (value instanceof String) return ((String) value).trim().length() == 0;
        if (value instanceof Collection) return ((Collection<? extends Object>) value).size() == 0;
        if (value instanceof Map) return ((Map<? extends Object, ? extends Object>) value).size() == 0;
        if (value instanceof CharSequence) return ((CharSequence) value).length() == 0;

        // These types would flood the log
        // Number covers: BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short
        if (value instanceof Boolean) return false;
        if (value instanceof Number) return false;
        //if (value instanceof Character) return false;
        if (value instanceof java.util.Date) return false;

        return false;
    }

}
