package org.ss.vendorapi.util;

import java.io.UnsupportedEncodingException;

import java.util.HashSet;
import java.util.Set;


import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {
	
	private static final Logger logger = System.getLogger("CommonUtils"); 
	
	/*
	 * This method is used to check whether an object (specifically String) is empty or not.
	 * If object is empty, then returnValue is returned.
	 */
	
	public static String checkForEmptyAndReturn(Object value, String returnValue){
		return (UtilValidate.isNotEmpty(value)) ? value.toString().trim() : returnValue;
	}
	
	/*
	 * This method is used to check whether an object (specifically Double) is empty or not.
	 * If object is empty, then null or defaultVal is returned (as the may be).
	 */
	
	public static Double checkAndConvertStrToDouble(Object value){
		return checkAndConvertStrToDouble(value, null);
	}
	
	public static Double checkAndConvertStrToDouble(Object value, Double defaultVal){
		Double valueD = defaultVal;
		
		if(UtilValidate.isNotEmpty(value)){
			try{
				valueD = Double.valueOf(value.toString());
			}
			catch(NumberFormatException e){
				System.out.print("Exception while converting " + value + " to Double : " + e.getMessage());
			}
		}
		return valueD;
	}
	
	
	/*
	 * This method is used for date formatting purpose. Specifically used for Month & Year formatting.
	 */
	
	
	
	public static String getDateFormattedValue(String format, String monthYear){
		SimpleDateFormat reqFormat = new SimpleDateFormat(format);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateObj = null;
		
		try{
			dateObj = dateFormat.parse("01/" + monthYear);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return reqFormat.format(dateObj);
	}
	
	/*
	 * This method is used for date formatting purpose. Used for any type of date formatting.
	 */
	
	public static String getDateFormattedValue(String format, Date dateObj){
		SimpleDateFormat sdfFormat = new SimpleDateFormat(format);
		return sdfFormat.format(dateObj);
	}
		
	// Method for getting comma separated ids from ObjList
	
	public static String getCommaSeparatedIdsFromObjList(List<Object> objList, int columnIndex){
		StringBuffer ids = new StringBuffer("");
		
		if(UtilValidate.isNotEmpty(objList)){
			Object[] objArr;
			boolean isFirstTime = true;
			
			for(Object obj: objList){
				objArr = (Object[]) obj;
				
				if(isFirstTime){
					isFirstTime = false;
					ids.append("'"+objArr[columnIndex]+"'");
				}
				else
					ids.append(",'"+objArr[columnIndex]+"'");
			}
		}
		
		return ids.toString();
	}
	
	/*
	 * This method is used to validate whether appServiceKey is valid or not.
	 * If appServiceKey is valid, then null is returned else UNAUTH_ACCESS FAIL is returned.
	 */
	
	public static ResponseEntity<?> validateAppServiceKey(String appServiceKey, String apikey){
		if(UtilValidate.isEmpty(appServiceKey))
			return createResponse(Constants.FAIL, Constants.UNAUTH_ACCESS, HttpStatus.UNAUTHORIZED);
		
		appServiceKey = appServiceKey.replace("%24","$");
		appServiceKey = appServiceKey.replace("%40","@");
	
		if(!apikey.equals(appServiceKey)){
//			System.out.println("#### KEY MISMATCHED");
			return createResponse(Constants.FAIL, Constants.UNAUTH_ACCESS, HttpStatus.UNAUTHORIZED);
		}
		
		return null;
	}

	/*
	 * This method is used to return statusMap (common for all API response)
	 */
	
	public static ResponseEntity<?> createResponse(String status, String statusMsg, HttpStatus httpStatus){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put(Parameters.status, status);
		statusMap.put(Parameters.statusMsg, statusMsg);
		statusMap.put(Parameters.statusCode, "PARAM_302");
		return new ResponseEntity<>(statusMap, httpStatus);
	}
	
    public static String generateSHA256(String input)
    {
    	try {
	        // Static getInstance method is called with hashing SHA
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	 
	        // digest() method called
	        // to calculate message digest of an input
	        // and return array of byte
	        //return md.digest(input.getBytes(StandardCharsets.UTF_8));
	        
	        return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8)));
    	}
        catch(NoSuchAlgorithmException e) {
        	e.printStackTrace();
			return null;
		}
    }
	     
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
 
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
 
        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
 
        return hexString.toString();
    }
    
    public static String generatehash512(String input)
    {
    	 try {
             // getInstance() method is called with algorithm SHA-512
             MessageDigest md = MessageDigest.getInstance("SHA-512");
   
             // digest() method is called
             // to calculate message digest of the input string
             // returned as array of byte
             byte[] messageDigest = md.digest(input.getBytes());
   
             // Convert byte array into signum representation
             BigInteger no = new BigInteger(1, messageDigest);
   
             // Convert message digest into hex value
             String hashtext = no.toString(16);
   
             // Add preceding 0s to make it 64 bit
             while (hashtext.length() < 128) {
                 hashtext = "0" + hashtext;
             }
   
             // return the HashText
             return hashtext;
         }
   
         // For specifying wrong message digest algorithms
         catch (NoSuchAlgorithmException e) {
             throw new RuntimeException(e);
         }
    }
    
    public static String generateMd5Hash(String input)
    {
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Boolean verifyHashMatch(String pgResponse, String salt)
    {
        Boolean isHashMatch = false;
        if (pgResponse.split("\\|").length > 2)
        {
            String hashToCalculate = pgResponse.substring(0, pgResponse.lastIndexOf("\\|") + 1) + salt;
            String calculatedHash = generateMd5Hash(hashToCalculate);
            String pgHash = generateMd5Hash(pgResponse.substring(pgResponse.lastIndexOf("\\|") + 1, pgResponse.length() - pgResponse.lastIndexOf("\\|") - 1));
            if (calculatedHash.toUpperCase() == pgHash.toUpperCase())
            {
                isHashMatch = true;
            }
        }
        else
        {
            isHashMatch = false;
        }
        return isHashMatch;
    }
    

    
    
    
    public static String smsMD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  
   	{ 
   		MessageDigest md;
   		md = MessageDigest.getInstance("SHA-1");
   		byte[] md5 = new byte[64];
   		md.update(text.getBytes("iso-8859-1"), 0, text.length());
   		md5 = md.digest();
   		return convertedToHex(md5);
   	}

       private static String convertedToHex(byte[] data) 
   	{ 
   		StringBuffer buf = new StringBuffer();

   		for (int i = 0; i < data.length; i++) 
   		{ 
   			int halfOfByte = (data[i] >>> 4) & 0x0F;
   			int twoHalfBytes = 0;

   			do 
   			{ 
   				if ((0 <= halfOfByte) && (halfOfByte <= 9)) 
   				{
   					buf.append( (char) ('0' + halfOfByte) );
   				}

   				else 
   				{
   					buf.append( (char) ('a' + (halfOfByte - 10)) );
   				}

   				halfOfByte = data[i] & 0x0F;

   			} while(twoHalfBytes++ < 1);
   		} 
   		return buf.toString();
   	}

       public static String smsHashGenerator(String userName, String senderId, String content, String secureKey) {
   	
   		StringBuffer finalString=new StringBuffer();
   		finalString.append(userName.trim()).append(senderId.trim()).append(content.trim()).append(secureKey.trim());
   		//		logger.info("Parameters for SHA-512 : "+finalString);
   		String hashGen=finalString.toString();
   		StringBuffer sb = null;
   		MessageDigest md;
   		try {
   			md = MessageDigest.getInstance("SHA-512");
   			md.update(hashGen.getBytes());
   			byte byteData[] = md.digest();
   			//convert the byte to hex format method 1
   			sb = new StringBuffer();
   			for (int i = 0; i < byteData.length; i++) {
   				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
   			}

   		} catch (NoSuchAlgorithmException e) {
   			
   			e.printStackTrace();
   		}
   		return sb.toString();
   	}
    
     /// For Payment
       public static boolean isValidChecksum(String message, String checksum,
   			String secret) {
//   		final String METHOD_NAME = "isValidChecksum()";
//   		CommonUtils.logProcStartTime(METHOD_NAME);

   		boolean isValid = false;
   		if (null != checksum && null != message) {
   			if (checksum.equals(generateCheksum(message, secret))) {
   				isValid = true;
   			}
   		}

//   		CommonUtils.logProcEndTime(METHOD_NAME);
   		return isValid;  
   		  
   	}
   			
       public static String generateCheksum(String message, String secret) {
   		final String METHOD_NAME = "generateCheksum()";
   		CommonUtils.logProcStartTime(METHOD_NAME);

   		try {
   			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
   			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(),
   					"HmacSHA256");
   			sha256_HMAC.init(secret_key);
   			byte raw[] = sha256_HMAC.doFinal(message.getBytes());
   			StringBuffer ls_sb = new StringBuffer();
   			for (int i = 0; i < raw.length; i++) {
   				ls_sb.append(char2hex(raw[i]));
   			}

   			CommonUtils.logProcEndTime(METHOD_NAME);
   			return ls_sb.toString();
   		} catch (Exception e) {
   			logger.log(Level.INFO, METHOD_NAME,
   						"Exception while generating checksum"
   								+ "for message : " + message, e);
   			
   			return null;
   		}

   	}

   	public static String char2hex(byte x) {
   		final String METHOD_NAME = "HmacSHA256()";
   		CommonUtils.logProcStartTime(METHOD_NAME);

   		char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
   				'B', 'C', 'D', 'E', 'F' };

   		char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };

   		CommonUtils.logProcEndTime(METHOD_NAME);
   		return (new String(c));
   	}

      

       
       


   	public static byte[] fromHexString(String s, int offset, int length)
   	{
   		if ((length%2) != 0)
   			return null;
   		byte[] byteArray = new byte[length/2];
   		int j = 0;
   		int end = offset+length;
   		for (int i = offset; i < end; i += 2)
   		{
   			int high_nibble = Character.digit(s.charAt(i), 16);
   			int low_nibble = Character.digit(s.charAt(i+1), 16);
   			if (high_nibble == -1 || low_nibble == -1)
   			{
   				// illegal format
   				return null;
   			}
   			byteArray[j++] = (byte)(((high_nibble << 4) & 0xf0) | (low_nibble & 0x0f));
   		}
   		return byteArray;
   	}

       /**
        * Returns Hex output of byte array
        */
		/*
		 * static String hex(byte[] input) {
		 * 
		 * // create a StringBuffer 2x the size of the hash array StringBuffer sb = new
		 * StringBuffer(input.length * 2); // retrieve the byte array data, convert it
		 * to hex // and add it to the StringBuffer for (int i = 0; i < input.length;
		 * i++) { sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
		 * sb.append(HEX_TABLE[input[i] & 0xf]); } return sb.toString(); }
		 */

       /**
        * This method is for creating a URL query string.
        * 
        * @param buf
        *            is the inital URL for appending the encoded fields to
        * @param fields
        *            is the input parameters from the order page
        */
       // Method for creating a URL query string
       void appendQueryFields(StringBuffer buf, Map fields) {

           // create a list
           List fieldNames = new ArrayList(fields.keySet());
           Iterator itr = fieldNames.iterator();

           // move through the list and create a series of URL key/value pairs
           while (itr.hasNext()) {
               String fieldName = (String) itr.next();
               String fieldValue = (String) fields.get(fieldName);

               if ((fieldValue != null) && (fieldValue.length() > 0)) {
                   // append the URL parameters
                   buf.append(URLEncoder.encode(fieldName));
                   buf.append('=');
                   buf.append(URLEncoder.encode(fieldValue));
               }

               // add a '&' to the end if we have more fields coming.
               if (itr.hasNext()) {
                   buf.append('&');
               }
           }

       } // appendQueryFields()

    public static void logMethodStartTime(String className, String name) {
    	logger.log(Level.INFO, "Inside " + className + " -> called method " + name + " -> Start DateTime - " + new Date());
    }
    
    public static void logMethodEndTime(String className, String name) {
    	logger.log(Level.INFO, "Inside " + className + " -> called method " + name + " -> End DateTime - " + new Date());
    }
    
    public static void logApiStartTime(String className, String name) {
    	logger.log(Level.INFO, "Inside " + className + " -> called API " + name + " -> Start DateTime - " + new Date());
    }
    
    public static void logApiEndTime(String className, String name) {
    	logger.log(Level.INFO, "Inside " + className + " -> called API " + name + " -> End DateTime - " + new Date());
    }
    
    public static void logProcStartTime(String name) {
    	logger.log(Level.INFO, "Called procedure " + name + " -> Start DateTime - " + new Date());
    }
    
    public static void logProcEndTime(String name) {
    	logger.log(Level.INFO, "Called procedure " + name + " -> End DateTime - " + new Date());
    }
    
    public static boolean isDebugLoggingEnabled() {
    	return true;
    }
    
    public static JsonNode convertJsonStringToJsonObject(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            // Handle JSON parsing exceptions
            e.printStackTrace();
            return null;
        }
    }

    private static final Set<String> ALLOWED_FILE_TYPES = new HashSet<>();

    static {
        // Initialize the set with allowed file types
        ALLOWED_FILE_TYPES.add("application/pdf"); // PDF
        ALLOWED_FILE_TYPES.add("image/jpeg");      // JPEG image
        ALLOWED_FILE_TYPES.add("image/png");       // PNG image
        ALLOWED_FILE_TYPES.add("image/gif");       // GIF image
    }

    public static boolean isValidFileType(String contentType) {
        return ALLOWED_FILE_TYPES.contains(contentType);
    }


    
}
