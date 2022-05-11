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
		//scan.close();
	    //////////////////////////////////////////////////////
	    
	    
		try {
			Socket misocket = new Socket("localhost",5000);
			
			DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());
			
			
			flujoSalida.writeUTF(mensaje);
			
			//flujoSalida.close();
			
			
			
			DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());
			
			String mensajeTexto = flujoEntrada.readUTF();
			
			if(mensajeTexto == "c") {
				
				System.out.println(mensajeTexto);
				
			}else {
				
				System.out.println("Error en la consulta: " + mensajeTexto);
				
			}
			
			
			
			//misocket.close();
			
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
}
