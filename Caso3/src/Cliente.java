import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.security.Key;
import java.security.PublicKey;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner; 


public class Cliente extends Thread{

	private static Encriptador ec;
	private static File archivoLlave;
	private static PublicKey llaveServidor;
	private String nombreCliente;
	private int numPaqueteCliente;


	public Cliente(String nombre, int idPaquete) {
		nombreCliente = nombre;
		numPaqueteCliente = idPaquete;
	}

	private void cambiarPaquete(int idPaquete) {
		numPaqueteCliente= idPaquete;
	}
	public static void main(String[] args) {
		ec = new Encriptador();
		archivoLlave = new File("./docs/llavePublicaServidor.txt");
		System.out.println("Tipo de cliente: \n1)Iterativo \n2)Concurrente");
		Scanner scan = new Scanner(System.in);  
		int opcion = scan.nextInt(); 
		switch (opcion) {
		case 1: 
			Cliente cliente = new Cliente("Nombre", 1);
			for (int i = 0; i < 32; i++) {
				//TODO acceder a la tabla
				cliente.protocolo(cliente.nombreCliente, cliente.numPaqueteCliente);
			}
			break;
		case 2:
			System.out.println("Ingrese el numero de clientes");
			int numClientes = scan.nextInt(); 
			Cliente[] clientes = new Cliente[numClientes];
			for (int i = 0; i < clientes.length; i++) {
				//TODO acceso a la tabla
				clientes[i] = new Cliente("nombre", 1);
				clientes[i].run();
			}
		default:
			throw new IllegalArgumentException("Unexpected value: " + opcion);
		}
	}
	@Override
	public void run() {
		protocolo(nombreCliente, numPaqueteCliente);
	}
	
	private String generateInt(int digits) {
		StringBuilder str = new StringBuilder();
		Random random = new Random();
		for(int i = 0; i < digits; i++) {
			str.append(random.nextInt(10));
		}
		return str.toString();
	}

	public void protocolo(String nombre, int idPaquete) {

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



				flujoSalida.writeUTF(ec.encryptMessageAsymmetric(nombre, llaveServidor));

				String confirmacion2 = flujoEntrada.readUTF();

				System.out.println(confirmacion2);
				if(true) {


					// El cliente env�a el identificador del paquete para el que est� buscando informaci�n.

					flujoSalida.writeUTF(Integer.toString(idPaquete));


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
				}
				else {
					System.out.println("Error buscando nombre en tabla");
				}

			} else {

				System.out.println("Termina protocolo");
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

