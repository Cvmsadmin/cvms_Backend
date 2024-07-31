package org.ss.vendorapi.config;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptSecurityUtil implements PasswordEncoder{

		private static final String UTF8_ENCODING_FORMAT = "ISO8859_1";

        private static final String DES3_ALGORITHM = "DESede";

        private static final String DES3_ALGORITHM_PAD = "DESede/CBC/PKCS5Padding";
        
        @Value("${encrypDecrypt_key}")
        private String secret_key;
        
        @Value("${encrypDecrypt_iv}")
        private String secret_iv;
       

        private static Cipher init(String key, String iv, int p_CipherMode) {
            Cipher m_cipher = null;
            try {

                // the key and iv are stored in hex encoded format in the config
                // files.
                byte[] m_key;
                byte[] m_iv;

                m_key = HexCodeHelper.decode(key.getBytes(UTF8_ENCODING_FORMAT));
                m_iv = HexCodeHelper.decode(iv.getBytes(UTF8_ENCODING_FORMAT));

                IvParameterSpec m_ivData = new IvParameterSpec(m_iv);
                AlgorithmParameters m_algParam = AlgorithmParameters
                        .getInstance(DES3_ALGORITHM);
                m_algParam.init(m_ivData);

                DESedeKeySpec m_desedeKeySpec = new DESedeKeySpec(m_key);

                SecretKeyFactory m_keyFactory = SecretKeyFactory
                        .getInstance(DES3_ALGORITHM);
                SecretKey m_secretKey = m_keyFactory
                        .generateSecret(m_desedeKeySpec);

                m_cipher = Cipher
                        .getInstance(DES3_ALGORITHM_PAD);
                m_cipher.init(p_CipherMode, m_secretKey, m_algParam);

            } catch (GeneralSecurityException gse) {
               //logger
            } catch (UnsupportedEncodingException uee) {
                //logger
            }
            return m_cipher;
        }

	@Override
	public String encode(CharSequence rawPassword) {

         String m_encryptedString = null;
         if (rawPassword == null) { return null; }
         try {

             // encode
             Cipher m_cipher = init(secret_key, secret_iv, Cipher.ENCRYPT_MODE);
             byte[] encrypted = m_cipher.doFinal(rawPassword.toString().getBytes());
             m_encryptedString = new String(HexCodeHelper.encode(encrypted));

         } catch (GeneralSecurityException gse) {
         //logger   
         }

         return m_encryptedString;
	}
	
	public String decrypt(String key, String iv, String p_encryptedString) {

        if (p_encryptedString == null) { return null; }
        try {

            Cipher m_cipher = init(key, iv, Cipher.DECRYPT_MODE);

            byte[] cipherText;

            cipherText = HexCodeHelper.decode(p_encryptedString
                    .getBytes(UTF8_ENCODING_FORMAT));

            int len = cipherText.length;
            byte[] clearText = new byte[len];
            int clearLen = m_cipher.doFinal(cipherText, 0, len, clearText);

            return new String(clearText, UTF8_ENCODING_FORMAT).substring(0, clearLen);

        } catch (GeneralSecurityException gse) {
            //logger
        } catch (UnsupportedEncodingException uee) {
            //logger
        }
        return null;
    }


	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String[] decodeDBPassword =  decrypt(secret_key, secret_iv, encodedPassword).split("(?<=\\G.{" + 10 + "})");

		String encodeDBPassword = encode(decodeDBPassword[1]);
		
        return rawPassword.equals(encodeDBPassword);
	}
	
	
	 public static class HexCodeHelper {

	        /**
	         * HexDecoder provides static hex-encoding and decoding methods. This is
	         * based on jakarta code but reproduced as there is a variety of buggy
	         * jakarta implementations.
	         */

	        static private final int BASELENGTH = 255;

	        static private final int LOOKUPLENGTH = 16;

	        static private byte[] hexNumberTable = new byte[BASELENGTH];

	        static private byte[] lookUpHexAlphabet = new byte[LOOKUPLENGTH];

	        /**
	         * Initialise lookup arrays from binary to hex & back. A little effort now
	         * makes the runtime performance much better
	         */

	        static {
	            for (int i = 0; i < BASELENGTH; i++) {
	                hexNumberTable[i] = -1;
	            }
	            for (int i = '9'; i >= '0'; i--) {
	                hexNumberTable[i] = (byte) (i - '0');
	            }
	            for (int i = 'F'; i >= 'A'; i--) {
	                hexNumberTable[i] = (byte) (i - 'A' + 10);
	            }
	            for (int i = 'f'; i >= 'a'; i--) {
	                hexNumberTable[i] = (byte) (i - 'a' + 10);
	            }

	            for (int i = 0; i < 10; i++)
	                lookUpHexAlphabet[i] = (byte) ('0' + i);
	            for (int i = 10; i <= 15; i++)
	                lookUpHexAlphabet[i] = (byte) ('A' + i - 10);
	        }

	        /**
	         * Byte to be tested if it is Base64 alphabet
	         * 
	         * @param p_octect
	         * @return <true>if the octect is Base64 alphabet
	         */
	        private static boolean isHex(byte p_octect) {
	            return (hexNumberTable[p_octect] != -1);
	        }

	        /**
	         * Checks that the array contains only Base64 alphabet
	         * 
	         * @param p_arrayOctect
	         * @return <true>if all the members of the array pass the hex test
	         * @@see HexDecoder#isHex(byte)
	         */
	        private static boolean isArrayByteHex(byte[] p_arrayOctect) {
	            if (p_arrayOctect == null) { return false; }

	            int m_length = p_arrayOctect.length;
	            if (m_length % 2 != 0) { return false; }

	            for (int i = 0; i < m_length; i++) {
	                if (HexCodeHelper.isHex(p_arrayOctect[i]) == false) return false;
	            }
	            return true;
	        }

	        /**
	         * Checks that the string contains only Base64 alphabet
	         * 
	         * @param p_arrayOctect
	         * @return <true>if all the character of the string pass the hex test
	         * @@see HexDecoder#isHex(byte)
	         */
	        private static boolean isHex(String p_str) {
	            if (p_str == null) { return false; }

	            return (isArrayByteHex(p_str.getBytes()));
	        }

	        /**
	         * array of byte to encode
	         * 
	         * @param binaryData
	         *            the data to encode
	         * @return encoded binary array
	         */
	        static public byte[] encode(byte[] p_binaryData) {
	            if (p_binaryData == null) { return null; }

	            int m_lengthData = p_binaryData.length;
	            int m_lengthEncode = m_lengthData * 2;
	            byte[] m_encodedData = new byte[m_lengthEncode];

	            for (int i = 0; i < m_lengthData; i++) {
	                m_encodedData[i * 2] = lookUpHexAlphabet[(p_binaryData[i] >> 4) & 0xf];
	                m_encodedData[i * 2 + 1] = lookUpHexAlphabet[p_binaryData[i] & 0xf];
	            }

	            return m_encodedData;
	        }

	        /**
	         * array of byte to decode
	         * 
	         * @param p_binaryData
	         *            the data to decode
	         * @return decoded binary array
	         */
	        static public byte[] decode(byte[] p_binaryData) {
	            if (p_binaryData == null) { return null; }

	            int m_lengthData = p_binaryData.length;
	            if (m_lengthData % 2 != 0) { return null; }

	            int m_lengthDecode = m_lengthData / 2;
	            byte[] m_decodedData = new byte[m_lengthDecode];

	            for (int i = 0; i < m_lengthDecode; i++) {
	                m_decodedData[i] = (byte) ((hexNumberTable[p_binaryData[i * 2]] << 4) | hexNumberTable[p_binaryData[i * 2 + 1]]);
	            }

	            return m_decodedData;
	        }
	    }
}

