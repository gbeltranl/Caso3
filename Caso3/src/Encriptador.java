import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;

public class Encriptador {

	private final String SYMMETRIC_ALGORITHM = "AES";
	private final String ASYMMETRIC_ALGORITHM = "RSA";
	private final String HMAC_ALGORITHM = "HmacSHA256";
	private final String PADDING_ALGORITHM = "AES/ECB/PKCS5Padding";

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
	
	public byte[] encryptDataSymmetric(String data, Key llave) throws Exception {  
		Cipher cipher = Cipher.getInstance (PADDING_ALGORITHM); // Crear un cifrado  
		cipher.init (Cipher.ENCRYPT_MODE, llave); // Inicializar 
		byte[] input = data.getBytes();
		cipher.update(input);
		byte[] cipherText = cipher.doFinal();
		return cipherText;  
	} 
	
	public byte[] encryptDataAsymmetric(String data, Key llave) throws Exception {  
		Cipher cipher = Cipher.getInstance (ASYMMETRIC_ALGORITHM); // Crear un cifrado  
		cipher.init (Cipher.ENCRYPT_MODE, llave); // Inicializar 
		byte[] input = data.getBytes();
		cipher.update(input);
		byte[] cipherText = cipher.doFinal();
		return cipherText;  
	} 

	public String decryptDataSymmetric(byte[] data, Key llave) throws Exception{  
		Cipher cipher = Cipher.getInstance (PADDING_ALGORITHM); // Crear un cifrado  
		cipher.init(Cipher.DECRYPT_MODE, llave); 
		cipher.update(data);
		byte[] decipherText = cipher.doFinal();
		return new String(decipherText, "UTF8"); 
	}
	
	public String decryptDataAsymmetric(byte[] data, Key llave) throws Exception{  
		Cipher cipher = Cipher.getInstance (ASYMMETRIC_ALGORITHM); // Crear un cifrado  
		cipher.init(Cipher.DECRYPT_MODE, llave); 
		cipher.update(data);
		byte[] decipherText = cipher.doFinal();
		return new String(decipherText, "UTF8"); 
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

}
