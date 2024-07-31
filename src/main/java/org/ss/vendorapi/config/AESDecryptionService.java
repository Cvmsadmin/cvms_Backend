package org.ss.vendorapi.config;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;

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
public class AESDecryptionService {

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

	public AESDecryptionService() {
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			saltLength = this.keySize / 4;
		} catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
//			System.out.println(e.getMessage());
		}
	}

	public AESDecryptionService(int keySize, int iterationCount) {
		this.keySize = keySize;
		this.iterationCount = iterationCount;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			saltLength = this.keySize / 4;
		} catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
//			System.out.println(e.getMessage());
		}
	}

	public String decrypt(String salt, String iv, String passPhrase, String cipherText) {
		try {
			SecretKey key = generateKey(salt, passPhrase);
			byte[] encrypted;
			if (dataType.equals(DataType.HEX)) {
				encrypted = fromHex(cipherText);
			} else {
				encrypted = fromBase64(cipherText);
			}
			byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, encrypted);
			return new String(Objects.requireNonNull(decrypted), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String decrypt(String passPhrase, String cipherText) {
		try {
			String salt = cipherText.substring(0, saltLength);
			int ivLength = IV_SIZE / 4;
			String iv = cipherText.substring(saltLength, saltLength + ivLength);
			String ct = cipherText.substring(saltLength + ivLength);
			return decrypt(salt, iv, passPhrase, ct);
			
		} catch (Exception e) {
			
//			System.out.println(e.getMessage());
			e.printStackTrace();		 
			 return null;
		}
	}

	private SecretKey generateKey(String salt, String passPhrase) {
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), fromHex(salt), iterationCount, keySize);
			return new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//			System.out.println(e.getMessage());
		}
		return null;
	}

	private static byte[] fromBase64(String str) {
		return DatatypeConverter.parseBase64Binary(str);
	}


	private static byte[] fromHex(String str) {
		return DatatypeConverter.parseHexBinary(str);
	}


	private byte[] doFinal(int mode, SecretKey secretKey, String iv, byte[] bytes) {
		try {
			cipher.init(mode, secretKey, new IvParameterSpec(fromHex(iv)));
			return cipher.doFinal(bytes);
		} catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException e) {
//			System.out.println(e.getMessage());
		}
		return null;
	}


}
