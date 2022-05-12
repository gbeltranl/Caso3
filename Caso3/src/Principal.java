import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Principal {

	

	public static void main(String[] args) throws Exception {
		Encriptador ec = new Encriptador();

		Scanner sc = new Scanner(System.in);
		KeyPair kp = ec.generateAsymmetricKeyPair();
		Key key = ec.generateSymmetricKey();
		KeyPair kpKeyPair = ec.generateAsymmetricKeyPair();
	    String txt = sc.nextLine();
	    //byte[] secretTxt = ec.encryptDataSymmetric(txt, key);
	    //String secretTxt2 = ec.encryptMessageAsymmetric(txt, kp.getPrivate());
	    //System.out.println("Secret Symmetric: " + secretTxt);
	    //System.out.println("HMAC " + ec.calcHmacSha256(key, secretTxt));
	    //System.out.println("Secret Asymmetric: " + secretTxt2);
	    //System.out.println("Decrypt Symmetric: "+ ec.decryptDataSymmetric(secretTxt, key));
	    //System.out.println("Decrypt Asymmetric: "+ ec.decryptMessageAsymmetric(secretTxt2, kp.getPublic()));
	    
	    
		
	    
	   
	}
}
