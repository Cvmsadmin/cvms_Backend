package org.ss.vendorapi.config;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

@Component
public class AESEncryptionService {

    public enum DataType {
        HEX,
        BASE64
    }
 
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String KEY_ALGORITHM = "AES";

    private final int IV_SIZE = 128;

    private int iterationCount = 1989;
    
    private int keySize = 256;

    private int saltLength;

    private final DataType dataType = DataType.BASE64;

    private Cipher cipher;

    public AESEncryptionService() {
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            setSaltLength(this.keySize / 4);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
//            System.out.println(e.getMessage());
        }
    }

    public AESEncryptionService(int keySize, int iterationCount) {
        this.keySize = keySize;
        this.iterationCount = iterationCount;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            setSaltLength(this.keySize / 4);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
//            System.out.println(e.getMessage());
        }
    }

    public String encrypt(String salt, String iv, String passPhrase, String plainText) {
        try {
            SecretKey secretKey = generateKey(salt, passPhrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, secretKey, iv, plainText.getBytes(StandardCharsets.UTF_8));
            String cipherText;

            if (dataType.equals(DataType.HEX)) {
                cipherText = toHex(encrypted);
            } else {
                cipherText = toBase64(encrypted);
            }
            return cipherText;
        } catch (Exception e) {
            return null;
        }
    }

    public String encrypt(String passPhrase, String plainText) {
        try {
            String salt = toHex(generateRandom(keySize / 8));
            String iv = toHex(generateRandom(IV_SIZE / 8));
            String cipherText = encrypt(salt, iv, passPhrase, plainText);
            return salt + iv + cipherText;
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey generateKey(String salt, String passPhrase) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), fromHex(salt), iterationCount, keySize);
            return new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String toBase64(byte[] ba) {
        return DatatypeConverter.printBase64Binary(ba);
    }

    private static byte[] fromHex(String str) {
        return DatatypeConverter.parseHexBinary(str);
    }

    private static String toHex(byte[] ba) {
        return DatatypeConverter.printHexBinary(ba);
    }

    private byte[] doFinal(int mode, SecretKey secretKey, String iv, byte[] bytes) {
        try {
            cipher.init(mode, secretKey, new IvParameterSpec(fromHex(iv)));
            return cipher.doFinal(bytes);
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
                | InvalidKeyException e) {
//            System.out.println(e.getMessage());
        }
        return null;
    }

    private static byte[] generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return randomBytes;
    }

	public int getSaltLength() {
		return saltLength;
	}

	public void setSaltLength(int saltLength) {
		this.saltLength = saltLength;
	}

}
