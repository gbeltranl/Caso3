import java.io.File;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Gabriel
 *
 */
public class Encriptador {

	private final String SYMMETRIC_ALGORITHM = "AES";
	private final String ASYMMETRIC_ALGORITHM = "RSA";
	private final String HMAC_ALGORITHM = "HmacSHA256";
	private final String PADDING_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	private String byteToStr(byte[] bytes) {
		String stringBytes = "";
		for (byte b : bytes) {
			stringBytes += " " + b;
		}
		return stringBytes;

	}
	
	private byte[] strToBytes(String stringBytes) {
		byte[] bytes = new byte[128];
		String[] byteStr = stringBytes.trim().split(" ");
		for (int i = 0; i < byteStr.length; i++) {
			Byte byteValue = (byte) Integer.parseInt(byteStr[i]);
			bytes[i] = byteValue;
		}
		return bytes;
	}
	
	public KeyPair generateAsymmetricKeyPair() {
		KeyPairGenerator keyGen = null;
		KeyPair keyPair = null;
		try {
			keyGen = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM);
			keyGen.initialize(1024);
			keyPair = keyGen.genKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return keyPair;

	}

	public Key generateSymmetricKey() {
		KeyGenerator keyGen = null;
		try {
			keyGen = KeyGenerator.getInstance(SYMMETRIC_ALGORITHM);
			keyGen.init(256);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyGen.generateKey();
	}

	public String encryptMessageSymmetric(String data, Key llave) {  
		try {
			Cipher cipher = Cipher.getInstance (PADDING_ALGORITHM); // Crear un cifrado  
			cipher.init (Cipher.ENCRYPT_MODE, llave); // Inicializar 
			byte[] input = data.getBytes();
			cipher.update(input);
			byte[] cipherText = cipher.doFinal();
			return byteToStr(cipherText);  

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 

	public String encryptMessageAsymmetric(String data, Key llave)  {
		try {
			Cipher cipher = Cipher.getInstance (ASYMMETRIC_ALGORITHM); // Crear un cifrado  
			cipher.init (Cipher.ENCRYPT_MODE, llave); // Inicializar 
			byte[] input = data.getBytes();
			cipher.update(input);
			byte[] cipherText = cipher.doFinal();
			return byteToStr(cipherText);  
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return null;

	} 

	public String decryptMessageSymmetric(String data, Key llave) throws Exception{ 
		try {
			Cipher cipher = Cipher.getInstance (PADDING_ALGORITHM); // Crear un cifrado  
			cipher.init(Cipher.DECRYPT_MODE, llave); 
			cipher.update(strToBytes(data));
			byte[] decipherText = cipher.doFinal();
			return new String(decipherText, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Funcion para desencriptar bytes previamente encriptados asimetricamente con la llave correspondiente
	 * @param data
	 * @param llave
	 * @return String desencriptado
	 * @throws Exception
	 */
	public String decryptMessageAsymmetric(String data, Key llave) {  
		try {
			Cipher cipher = Cipher.getInstance (ASYMMETRIC_ALGORITHM); // Crear un cifrado  
			cipher.init(Cipher.DECRYPT_MODE, llave); 
			cipher.update(strToBytes(data));
			byte[] decipherText = cipher.doFinal();
			return new String(decipherText, "UTF8"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public byte[] calcHmacSha256(Key secretKey, byte[] message) {
		byte[] hmacSha256 = null;
		try {
			Mac mac = Mac.getInstance(HMAC_ALGORITHM);
			mac.init(secretKey);
			hmacSha256 = mac.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmacSha256;
	}

	public String keyToString(Key key) {
		byte[] keyByte = key.getEncoded();
		String keyString = Base64.getEncoder().encodeToString(keyByte);
		return keyString;
	}
	public PublicKey fileToKey(File keyFile) {
		PublicKey key = null;
		try {
			Scanner sc = new Scanner(keyFile);
			sc.nextLine();
			sc.nextLine();
			String[] mod = sc.nextLine().split(":");
			String[] exp = sc.nextLine().split(":");
			BigInteger modulus = new BigInteger(mod[1].trim());
			BigInteger exponent = new BigInteger(exp[1].trim());
			RSAPublicKeySpec x =  new RSAPublicKeySpec(modulus, exponent);
			KeyFactory kf  = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM);
			key = kf.generatePublic(x);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return key;
	}

	public String encryptKeyAsymmetric(Key data, Key llave) {
		try {
			Cipher cipher = Cipher.getInstance (ASYMMETRIC_ALGORITHM); // Crear un cifrado  
			cipher.init (Cipher.ENCRYPT_MODE, llave); // Inicializar 
			byte[] encodedKey = Base64.getEncoder().encode(data.getEncoded());
			cipher.update(encodedKey);
			byte[] cipherText = cipher.doFinal();
			return byteToStr(cipherText);  
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return null;
	}
	
	public Key decryptKeyAsymmetric(String data, Key llave) {
		try {
			Cipher cipher = Cipher.getInstance (ASYMMETRIC_ALGORITHM); // Crear un cifrado  
			cipher.init (Cipher.DECRYPT_MODE, llave); // Inicializar 
			cipher.update(strToBytes(data));
			byte[] cipherText = cipher.doFinal();
			byte[] decodedKey = Base64.getDecoder().decode(cipherText);

			Key key = new SecretKeySpec(decodedKey, 0, decodedKey.length, SYMMETRIC_ALGORITHM); 
			return key;  
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return null;
	}

}
