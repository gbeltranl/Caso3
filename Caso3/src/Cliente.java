import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.Scanner; 


public class Cliente {
	
		public static void main(String[] args) {
			
			Cliente cliente1 = new Cliente();
			
			cliente1.protocolo();
		
		/**
		while(true) {
		Scanner scan = new Scanner(System.in);  
	    System.out.println("Ingrese Mensaje");

	    String mensaje = scan.nextLine();  
	
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
*/		
}

		
public void protocolo() {
	
			System.out.println("Iniciando Cliente...");
			
			System.out.println("Leyendo llave pública");
			
			//TO-DO LEER LLAVE PUBLICA DEL SERVIDOR

			try {
				
				//Conexion Socket
				Socket misocket = new Socket("localhost",5000);
				
				DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());
				
				
				//Inicio Sesion
				Scanner scan = new Scanner(System.in);  
				
			    System.out.println("Desea Iniciar sesión? : a) Sí  b) No");

			    String mensaje = scan.nextLine(); 
			    
			    if(mensaje.equals("a") ) {
			    	
			    	flujoSalida.writeUTF("Iniciar sesion");
			    	
			    	System.out.println("Iniciando Sesión...");
			    	
			    	flujoSalida.close();
			    	
			
			    }else if(mensaje.equals("b")) {
			    	
			    	System.out.println("Salió");
			    }
				
				
			//	flujoSalida.writeUTF("Iniciar sesion");
				
				//flujoSalida.close();
				
				
				/*
				DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());
				
				String mensajeTexto = flujoEntrada.readUTF();
				
				if(mensajeTexto == "c") {
					
					System.out.println(mensajeTexto);
					
				}else {
					
					System.out.println("Error en la consulta: " + mensajeTexto);
					
				}
				*/
				
				
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
			
			//Leer la llave pública del servidor
			System.out.println("Leer llave pública...");
			
			//Iniciando Sesión
			System.out.println("Iniciando sesión");
			
			//Enviando reto
			System.out.println("Enviando reto...");
			
			//Recibir reto cifrado
			System.out.println("Recibir reto cifrado...");
			
			//Validar cifrado
			System.out.println("Validando cifrado...");
			
			//Generar llave simetrica
			System.out.println("LLave simetrica con llave publica...");
			
			//El cliente envía nombre y espera mensaje de confirmación
			
			
			//Envio identificador del paquete


			//Recibir Estado del paquete


			//Enviar mensaje de confirmacion
			
			//Recibe código de resumen
			
			//Validar intedridad de información 
			
		}
}

