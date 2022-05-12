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
	private String numPaqueteCliente;
	private static String matriz[][] = new String[2][32];

	public Cliente(String nombre, String idPaquete) {
		nombreCliente = nombre;
		numPaqueteCliente = idPaquete;
	}


	public static void main(String[] args) {
		matriz[0][0] = "Juan";
		matriz[0][1] = "Pedro";
		matriz[0][2] = "Luis";
		matriz[0][3] = "Esteban";
		matriz[0][4] = "Cristian";
		matriz[0][5] = "Felipe";
		matriz[0][6] = "Angie";
		matriz[0][7] = "Sofia";
		matriz[0][8] = "Humberto";
		matriz[0][9] = "Camila";
		matriz[0][10] = "Maria";
		matriz[0][11] = "Lorena";
		matriz[0][12] = "Jaider";
		matriz[0][13] = "Gabriel";
		matriz[0][14] = "Wilton";
		matriz[0][15] = "Wilson";
		matriz[0][16] = "Julia";
		matriz[0][17] = "Pablo";
		matriz[0][18] = "Nicolas";
		matriz[0][19] = "Alfredo";
		matriz[0][20] = "Catalina";
		matriz[0][21] = "Pilar";
		matriz[0][22] = "Ivan";
		matriz[0][23] = "Tony";
		matriz[0][24] = "Carlos";
		matriz[0][25] = "Santiago";
		matriz[0][26] = "Andres";
		matriz[0][27] = "Laura";
		matriz[0][28] = "Liliana";
		matriz[0][29] = "Andrea";
		matriz[0][30] = "Fernanda";
		matriz[0][31] = "Silvia";


		matriz[1][0] = "1001";
		matriz[1][1] = "1002";
		matriz[1][2] = "1003";
		matriz[1][3] = "1004";
		matriz[1][4] = "1005";
		matriz[1][5] = "1006";
		matriz[1][6] = "1007";
		matriz[1][7] = "1008";
		matriz[1][8] = "1009";
		matriz[1][9] = "1010";
		matriz[1][10] = "1011";
		matriz[1][11] = "1012";
		matriz[1][12] = "1013";
		matriz[1][13] = "1014";
		matriz[1][14] = "1015";
		matriz[1][15] = "1016";
		matriz[1][16] = "1017";
		matriz[1][17] = "1018";
		matriz[1][18] = "1019";
		matriz[1][19] = "1019";
		matriz[1][20] = "1020";
		matriz[1][21] = "1021";
		matriz[1][22] = "1022";
		matriz[1][23] = "1023";
		matriz[1][24] = "1024";
		matriz[1][25] = "1025";
		matriz[1][26] = "1026";
		matriz[1][27] = "1027";
		matriz[1][28] = "1028";
		matriz[1][29] = "1029";
		matriz[1][30] = "1030";
		matriz[1][31] = "1031";
		ec = new Encriptador();
		archivoLlave = new File("./docs/llavePublicaServidor.txt");
		System.out.println("Tipo de cliente: \n1)Iterativo \n2)Concurrente");
		Scanner scan = new Scanner(System.in);  
		int opcion = scan.nextInt(); 
		switch (opcion) {
		case 1: 

			Cliente cliente = new Cliente(matriz[0][1], matriz[1][1]);
			for (int i = 0; i < 32; i++) {

				cliente.protocolo(cliente.nombreCliente, cliente.numPaqueteCliente);
			}
			break;
		case 2:
			System.out.println("Ingrese el numero de clientes");
			int numClientes = scan.nextInt(); 
			Cliente[] clientes = new Cliente[numClientes];
			for (int i = 0; i < clientes.length; i++) {
				//TODO acceso a la tabla
				clientes[i] = new Cliente(matriz[0][i], matriz[1][i]);
				clientes[i].run();
			}
			break;
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

	public void protocolo(String nombre, String numPaqueteCliente2) {

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
				if(confirmacion2.equals("ACK")) {


					// El cliente env�a el identificador del paquete para el que est� buscando informaci�n.

					flujoSalida.writeUTF(ec.encryptMessageAsymmetric(numPaqueteCliente2, llaveServidor));


					// TODO Recibe el estado del paquete
					String estadoPaquete = flujoEntrada.readUTF();
					estadoPaquete = ec.decryptMessageAsymmetric(estadoPaquete, llaveServidor);
					System.out.println(estadoPaquete);

					flujoSalida.writeUTF("ACK");
					String hmac = flujoEntrada.readUTF();

					// TODO El cliente debe validar la integridad de la informaci�n recibida y si la validaci�n es exitosa entones debe
					//desplegar la informaci�n en consola. 

					if(hmac.equals(ec.calcHmacSha256(llaveSimetrica, estadoPaquete))) {

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

