package org.ss.vendorapi.modal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UPPCLModel {
	
	 /** Holds the set of getter methods for classes extending this one. */
    private static Map<Class<?>,Method[]> getterMap = new HashMap<Class<?>,Method[]>();
    
    /** Empty object array used throughout when invoking methods via reflection. */
    private static final Object[] EMPTY_ARRAY = new Object[0];
    
    protected final boolean checkEquality(Object o1, Object o2) {
        return (o1 == o2 || o1 != null && o1.equals(o2));
    }
    
    /**
     * <p>Retrieves the unqualified name of the object's class. This is the
     * name of the class without the leading package name or period.</p>
     */
    protected String unqualifiedClassName() {
        String name = getClass().getName();
        return name.substring( name.lastIndexOf(".") + 1, name.length() );
    }

  
    /**
     * <p>Dynamically creates the string value of the object using the
     * reflection API.  Outputs a string in the format field=value for
     * all fields accessible through public API accessor methods.</p>
     *
     * <p>The output is similar to that used by the Collections API, with
     * the addition of the ClassName.  An example follows:</p>
     *
     * <pre>{Class=MyClass, Foo=bar, myString=whee}</pre>
     */
    public String toString() {
        StringBuffer buffer = null;

        // Get all the getter methods, and presize buffer roughly
        Method[] methods = accessorMethods();
        buffer = new StringBuffer(20 + (50 * methods.length));

        // Now, let's start with the ClassName
        buffer.append("{");
        buffer.append("Class=");
        buffer.append(unqualifiedClassName());

        // Then loop through and add all the non-static fields
        for (int i=0; i<methods.length; ++i) {
            try {
                buffer.append(", ");
                // For the methods whose  name starts with "is"
                if (methods[i].getName().startsWith("is")){
                    buffer.append( methods[i].getName().substring
                            (2, methods[i].getName().length()));
                }
                // For the methods whose  name starts with "get"
                else{
                    buffer.append( methods[i].getName().substring
                            (3, methods[i].getName().length()));
                }
                buffer.append( "=" );
                buffer.append( methods[i].invoke(this, EMPTY_ARRAY) );
            }
            catch (Exception iae) {
            	iae.printStackTrace();
            }
        }

        buffer.append("}");

        return buffer.toString();
    }
    
    /**
     * <p>Providers an implementation of the hashCode method that is safe, but
     * slow. Calculates a hashCode by XORing the hashCodes of all fields
     * accessible through the methods returned by <code>accessorMethods</code>.
     * This ensures that developers can never forget to implement a safe
     * hashCode method. However, this method should be overridden in most
     * circumstances when a faster hashcode is available.</p>
     */
    public int hashCode() {
        Method[] methods = accessorMethods();
        int hashcode = 0;
        try {
            for (int i=0; i<methods.length; ++i) {
                Object field = methods[i].invoke(this, EMPTY_ARRAY);
                if (field != null) hashcode = hashcode ^ field.hashCode();
            }
        }
        catch (Exception e) {
            // Should never happen
            hashcode = super.hashCode();
        }

        return hashcode;
    }

    /**
     * <p>Determines the equality of the object using information available
     * from the reflection API. The other object instance is considered to be
     * equal if it is non-null, an instance of the same class as the object
     * upon which <code>equals()</code> was invoked (note that assignability
     * is not enough in this case - the classes must be the same), and all
     * fields accessible through the accessorMethods of the class are equal
     * according to their own implementation of the <code>equals</code>
     * method.</p>
     */
    public boolean equals(Object other) {
        boolean isEqual = true;
        // First check that the other isn't null, and that it's of the same
        // class, and then do the real comparison using Fields again.
        if ( other == null ) {
            isEqual = false;
        }
        else if ( !getClass().equals( other.getClass() ) ) {
            isEqual = false;
        }
        else {
            Method[] methods = accessorMethods();

            // Loop through comparing fields, with a short circuit
            for (int i=0; i<methods.length && isEqual; ++i) {
                try {
                    Object mine   = methods[i].invoke(this,  EMPTY_ARRAY);
                    Object others = methods[i].invoke(other, EMPTY_ARRAY);
                    isEqual =  checkEquality(mine, others);
                }
                catch (Exception e) { /* That's unpossible! */ }
            }
        }
        return isEqual;
    }
    
    /**
     * <p>Retrieves all accessor methods for properties in the class. A method
     * is deemed to be an accessor if it is declared in this class, or any
     * superclass, has no parameters, is publicly accessible and has a name
     * starting with the String "get".</p>
     */
    protected Method[] accessorMethods() {
        Method[] methods = getterMap.get( getClass() );
        if (methods == null) {
            methods = getClass().getMethods();
            List<Method> getters = new ArrayList<Method>(methods.length / 2);
            
            // Pop all the getters into a collection
            for (int i=0; i<methods.length; ++i) {
                if ( methods[i].getParameterTypes().length == 0 &&
                        (methods[i].getName().startsWith("get") ||
                        methods[i].getName().startsWith("is")) &&
                        !methods[i].getName().equals("getClass") ) {
                    getters.add(methods[i]);
                }
            }
            methods = (Method[]) getters.toArray(new Method[getters.size()]);
            getterMap.put(getClass(), methods);
        }

        return methods;
    }
    
}
