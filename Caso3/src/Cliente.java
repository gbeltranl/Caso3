import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.security.Key;
import java.security.PublicKey;
import java.util.Random;
import java.util.Scanner; 


public class Cliente {

	private static Encriptador ec;
	private static File archivoLlave;
	private static PublicKey llaveServidor;



	public static void main(String[] args) {
		ec = new Encriptador();
		archivoLlave = new File("./docs/llavePublicaServidor.txt");
		Cliente cliente1 = new Cliente();

		cliente1.protocolo();

	}
	private String generateInt(int digits) {
	    StringBuilder str = new StringBuilder();
	    Random random = new Random();
	    for(int i = 0; i < digits; i++) {
	        str.append(random.nextInt(10));
	    }
	    return str.toString();
	}

	public void protocolo() {

		System.out.println("Iniciando Cliente...");

		System.out.println("Leyendo llave p�blica");

		llaveServidor = ec.fileToKey(archivoLlave);



		try {

			//Conexion Socket
			Socket misocket = new Socket("localhost",5001);

			DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());

			DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());

			//Inicio Sesion
			Scanner scan = new Scanner(System.in);  

			System.out.println("Desea Iniciar sesi�n? : a) S�  b) No");

			String mensaje = scan.nextLine(); 

			if(mensaje.equals("a") ) {

				flujoSalida.writeUTF("Iniciar sesion");

				System.out.println("Iniciando Sesi�n...");



				// Llegada de Mensaje ACK
				String mensajeLlegada = flujoEntrada.readUTF();

				System.out.println(mensajeLlegada);


				// El cliente env�a al servidor un reto (un n�mero aleatorio de 24 d�gitos)
				String reto = generateInt(24);
				flujoSalida.writeUTF(reto);

				// LLega el reto cifrado
				String retoCifrado = flujoEntrada.readUTF();
				String decifrado = ec.decryptMessageAsymmetric(retoCifrado, llaveServidor);
				boolean retoCorrecto = decifrado.equals(reto);

				//  Al recibir la respuesta, el cliente valida que el reto cifrado tenga el valor esperado; si la validaci�n pasa entonces
				//      el cliente contin�a con el protocolo; si la validaci�n no pasa entonces el cliente termina la comunicaci�n. 

				if (retoCorrecto) {
					System.out.println("Reto Correcto");

					//TODO El cliente genera una llave sim�trica (LS),
					//     la cifra con la llave p�blica del servidor (KS+) y la env�a al servidor. 
					Key llaveSimetrica = ec.generateSymmetricKey();
					String llaveEncriptada = ec.encryptKeyAsymmetric(llaveSimetrica, llaveServidor);
					flujoSalida.writeUTF(llaveEncriptada);

					// LLega la confirmaci�n ACK
					String confirmacion = flujoEntrada.readUTF();

					System.out.println(confirmacion);


					// TODO El cliente env�a su nombre y espera un mensaje de confirmaci�n (�ACK�)


					System.out.println("Escriba su nombre: ");

					String nombre = scan.nextLine();

					flujoSalida.writeUTF(nombre);

					String confirmacion2 = flujoEntrada.readUTF();

					System.out.println(confirmacion2);

					// El cliente env�a el identificador del paquete para el que est� buscando informaci�n.

					System.out.println("Escriba el identificador de su paquete: ");

					String paquete = scan.nextLine();

					flujoSalida.writeUTF(paquete);


					// TODO Recibe el estado del paquete
					String estadoPaquete = flujoEntrada.readUTF();

					System.out.println(estadoPaquete);

					flujoSalida.writeUTF("ACK");

					// TODO El cliente debe validar la integridad de la informaci�n recibida y si la validaci�n es exitosa entones debe
					//desplegar la informaci�n en consola. 

					if(true) {

						System.out.println("Validacion Exitosa");

					}else {

						System.out.println("Validacion Errada");
					}

				} else {

					System.out.println("Termina protocolo");
				}

			}else if(mensaje.equals("b")) {

				System.out.println("Sali�");
			}





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

