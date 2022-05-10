import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Principal {

	public static void main(String[] args) {
		
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
		    KeyPair keypair = keyGen.genKeyPair();
		    PrivateKey privateKey = keypair.getPrivate();
		    PublicKey publicKey = keypair.getPublic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    int a = 0;
	}
}
