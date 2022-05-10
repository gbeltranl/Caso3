import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.Scanner; 


public class Cliente {
	
		public static void main(String[] args) {
	
		while(true) {
		Scanner scan = new Scanner(System.in);  
	    System.out.println("Ingrese Mensaje");

	    String mensaje = scan.nextLine();  
	    //System.out.println("El mensaje es: " + mensaje); 
		
	    //////////////////////////////////////////////////////
	    
	    
		try {
			Socket misocket = new Socket("localhost",5000);
			
			DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());
			
		//	String mensajePrueba = "Mensaje de prueba";
			
			flujoSalida.writeUTF(mensaje);
			
			flujoSalida.close();
			
			/**
			Socket misocket2 = new Socket("localhost",5000);
			
			DataInputStream flujoEntrada = new DataInputStream(misocket2.getInputStream());
			
			String mensajeTexto = flujoEntrada.readUTF();
			
			System.out.println(mensajeTexto);
			*/
			
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unk problem");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException problem");
			e.printStackTrace();
		}
	
	}
		}
	//public Cliente() {
		
	//}
}
