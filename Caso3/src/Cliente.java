import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.Scanner; 


public class Cliente {
	
		public static void main(String[] args) {
			
			Cliente cliente1 = new Cliente();
			
			cliente1.protocolo();

}

		
public void protocolo() {
	
			System.out.println("Iniciando Cliente...");
			
			System.out.println("Leyendo llave pública");
			
			//TODO LEER LLAVE PUBLICA DEL SERVIDOR
			
			

			try {
				
				//Conexion Socket
				Socket misocket = new Socket("localhost",5001);
				
				DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());
				
				DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());
				
				//Inicio Sesion
				Scanner scan = new Scanner(System.in);  
				
			    System.out.println("Desea Iniciar sesión? : a) Sí  b) No");

			    String mensaje = scan.nextLine(); 
			    
			    if(mensaje.equals("a") ) {
			    	
			    	flujoSalida.writeUTF("Iniciar sesion");
			    	
			    	System.out.println("Iniciando Sesión...");
			    	
			
			    	
			    	// Llegada de Mensaje ACK
			    	String mensajeLlegada = flujoEntrada.readUTF();
			    	
			    	System.out.println(mensajeLlegada);
			    	
			    	
			    	//TODO El cliente envía al servidor un reto (un número aleatorio de 24 dígitos)
			    	
			    	flujoSalida.writeUTF("Mensaje de 24");
			    	
			    	// LLega el reto cifrado
			    	String retoCifrado = flujoEntrada.readUTF();
			    	
			    	System.out.println(retoCifrado);
			
			
			    	// TODO Al recibir la respuesta, el cliente valida que el reto cifrado tenga el valor esperado; si la validación pasa entonces
			    	//      el cliente continúa con el protocolo; si la validación no pasa entonces el cliente termina la comunicación. 
			    	
			    	if (true) {
			    		
			    		//TODO El cliente genera una llave simétrica (LS),
			    		//     la cifra con la llave pública del servidor (KS+) y la envía al servidor. 
			    		flujoSalida.writeUTF("Llave Simétrica");
			    		
			    		// LLega la confirmación ACK
			    		String confirmacion = flujoEntrada.readUTF();
			    		
			    		System.out.println(confirmacion);
			    		
			    		
			    		// TODO El cliente envía su nombre y espera un mensaje de confirmación (¨ACK¨)
			    		
						
					    System.out.println("Escriba su nombre: ");

					    String nombre = scan.nextLine();
			    		
			    		flujoSalida.writeUTF(nombre);
			    		
			    		String confirmacion2 = flujoEntrada.readUTF();
			    		
			    		System.out.println(confirmacion2);
			    		
			    		// El cliente envía el identificador del paquete para el que está buscando información.
			    		
			    		System.out.println("Escriba el identificador de su paquete: ");

					    String paquete = scan.nextLine();

			    		flujoSalida.writeUTF(paquete);
			    		
			    		
			    		// TODO Recibe el estado del paquete
			    		String estadoPaquete = flujoEntrada.readUTF();
			    		
			    		System.out.println(estadoPaquete);
			    		
			    		flujoSalida.writeUTF("ACK");
			    		
			    		// TODO El cliente debe validar la integridad de la información recibida y si la validación es exitosa entones debe
			    		//desplegar la información en consola. 
			    		
			    		if(true) {
			    			
			    			System.out.println("Validacion Exitosa");
			    			
			    		}else {
			    			
			    			System.out.println("Validacion Errada");
			    		}
			    		
			    	} else {
			    		
			    		System.out.println("Termina protocolo");
			    	}
			    	
			    }else if(mensaje.equals("b")) {
			    	
			    	System.out.println("Salió");
			    }
				
			    
				
			//	flujoSalida.writeUTF("Iniciar sesion");
				
				//flujoSalida.close();
				
				
				/*
				DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());
				
				String mensajeTexto = flujoEntrada.readUTF();
				
				
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
			
			/**
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
		
			*/
		}
}

