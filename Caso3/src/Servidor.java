import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 */

/**
 * @author Gabriel
 *
 */
public class Servidor {
	private static Encriptador ec;
	private static KeyPair kp;
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	private String nombreCliente;
	private long identificadorPaquete;
	private String estadoPaquete;
	static String matriz[][] = new String[3][32];
	
	public static void main(String[] args) {
		ec = new Encriptador();
		kp = ec.generateAsymmetricKeyPair();
		publicKey = kp.getPublic();
		privateKey = kp.getPrivate();
		File archivo = new File("./docs/llavePublicaServidor.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(archivo);
			String keyString = publicKey.toString();
			fw.write(keyString);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		MarcoServidor miMarco = new MarcoServidor(publicKey, privateKey);

		miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



	}
}
class MarcoServidor extends JFrame implements Runnable{
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Encriptador ec;
	public MarcoServidor(PublicKey publicKey2, PrivateKey privateKey2) {
		ec = new Encriptador();
		
		publicKey = publicKey2;
		
		privateKey = privateKey2;
		
		setBounds(1200,300,280,350);

		JPanel miLamina = new JPanel();

		miLamina.setLayout(new BorderLayout());

		areaTexto = new JTextArea();

		miBoton = new JButton("Enviar");

		add(miBoton);

		miLamina.add(areaTexto,BorderLayout.CENTER);

		add(miLamina);

		setVisible(true);

		Thread miHilo = new Thread(this);


		miHilo.start();



	}
	
	

	private JTextArea areaTexto;

	private JButton miBoton;

	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println("Estoy a la escucha");
		
		Servidor.matriz[0][0] = "Juan";
		Servidor.matriz[0][1] = "Pedro";
		Servidor.matriz[0][2] = "Luis";
		Servidor.matriz[0][3] = "Esteban";
		Servidor.matriz[0][4] = "Cristian";
		Servidor.matriz[0][5] = "Felipe";
		Servidor.matriz[0][6] = "Angie";
		Servidor.matriz[0][7] = "Sofia";
		Servidor.matriz[0][8] = "Humberto";
		Servidor.matriz[0][9] = "Camila";
		Servidor.matriz[0][10] = "Maria";
		Servidor.matriz[0][11] = "Lorena";
		Servidor.matriz[0][12] = "Jaider";
		Servidor.matriz[0][13] = "Gabriel";
		Servidor.matriz[0][14] = "Wilton";
		Servidor.matriz[0][15] = "Wilson";
		Servidor.matriz[0][16] = "Julia";
		Servidor.matriz[0][17] = "Pablo";
		Servidor.matriz[0][18] = "Nicolas";
		Servidor.matriz[0][19] = "Alfredo";
		Servidor.matriz[0][20] = "Catalina";
		Servidor.matriz[0][21] = "Pilar";
		Servidor.matriz[0][22] = "Ivan";
		Servidor.matriz[0][23] = "Tony";
		Servidor.matriz[0][24] = "Carlos";
		Servidor.matriz[0][25] = "Santiago";
		Servidor.matriz[0][26] = "Andres";
		Servidor.matriz[0][27] = "Laura";
		Servidor.matriz[0][28] = "Liliana";
		Servidor.matriz[0][29] = "Andrea";
		Servidor.matriz[0][30] = "Fernanda";
		Servidor.matriz[0][31] = "Silvia";
		
		
		Servidor.matriz[1][0] = "1001";
		Servidor.matriz[1][1] = "1002";
		Servidor.matriz[1][2] = "1003";
		Servidor.matriz[1][3] = "1004";
		Servidor.matriz[1][4] = "1005";
		Servidor.matriz[1][5] = "1006";
		Servidor.matriz[1][6] = "1007";
		Servidor.matriz[1][7] = "1008";
		Servidor.matriz[1][8] = "1009";
		Servidor.matriz[1][9] = "1010";
		Servidor.matriz[1][10] = "1011";
		Servidor.matriz[1][11] = "1012";
		Servidor.matriz[1][12] = "1013";
		Servidor.matriz[1][13] = "1014";
		Servidor.matriz[1][14] = "1015";
		Servidor.matriz[1][15] = "1016";
		Servidor.matriz[1][16] = "1017";
		Servidor.matriz[1][17] = "1018";
		Servidor.matriz[1][18] = "1019";
		Servidor.matriz[1][19] = "1019";
		Servidor.matriz[1][20] = "1020";
		Servidor.matriz[1][21] = "1021";
		Servidor.matriz[1][22] = "1022";
		Servidor.matriz[1][23] = "1023";
		Servidor.matriz[1][24] = "1024";
		Servidor.matriz[1][25] = "1025";
		Servidor.matriz[1][26] = "1026";
		Servidor.matriz[1][27] = "1027";
		Servidor.matriz[1][28] = "1028";
		Servidor.matriz[1][29] = "1029";
		Servidor.matriz[1][30] = "1030";
		Servidor.matriz[1][31] = "1031";
		
		Servidor.matriz[2][0] = "Enviado";
		Servidor.matriz[2][1] = "Entregado";
		Servidor.matriz[2][2] = "En camino";
		Servidor.matriz[2][3] = "Enviado";
		Servidor.matriz[2][4] = "Entregado";
		Servidor.matriz[2][5] = "En camino";
		Servidor.matriz[2][6] = "En camino";
		Servidor.matriz[2][7] = "En camino";
		Servidor.matriz[2][8] = "Enviado";
		Servidor.matriz[2][9] = "Entregado";
		Servidor.matriz[2][10] = "Enviado";
		Servidor.matriz[2][11] = "Entregado";
		Servidor.matriz[2][12] = "En camino";
		Servidor.matriz[2][13] = "En camino";
		Servidor.matriz[2][14] = "Enviado";
		Servidor.matriz[2][15] = "En camino";
		Servidor.matriz[2][16] = "En camino";
		Servidor.matriz[2][17] = "En camino";
		Servidor.matriz[2][18] = "Enviado";
		Servidor.matriz[2][19] = "Entregado";
		Servidor.matriz[2][20] = "En camino";
		Servidor.matriz[2][21] = "En camino";
		Servidor.matriz[2][22] = "Enviado";
		Servidor.matriz[2][23] = "Entregado";
		Servidor.matriz[2][24] = "Enviado";
		Servidor.matriz[2][25] = "Enviado";
		Servidor.matriz[2][26] = "Entregado";
		Servidor.matriz[2][27] = "Enviado";
		Servidor.matriz[2][28] = "Enviado";
		Servidor.matriz[2][29] = "Entregado";
		Servidor.matriz[2][30] = "Entregado";
		Servidor.matriz[2][31] = "Enviado";
		
		for (int x=0; x < Servidor.matriz.length; x++) {
			  for (int y=0; y < Servidor.matriz[x].length; y++) {
			    System.out.println (Servidor.matriz[x][y]);
			  }
			}

		try {
			ServerSocket servidor = new ServerSocket(5001);

			while(true) {

				Socket miSocket = servidor.accept();

				DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());


				DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());

				String mensajeTexto = flujoEntrada.readUTF();

				areaTexto.append("\n"+ mensajeTexto);



				// Env�o de mensaje ACK
				flujoSalida.writeUTF("ACK");
				//

				// Llegada de mensaje de 24 digitos
				String reto = flujoEntrada.readUTF();

				areaTexto.append("\n"+ reto);

				//Cifrar reto con llave privada y enviar al cliente
				long inicioCifradoAsimetrico = System.currentTimeMillis();
				String retoCifrado = ec.encryptMessageAsymmetric(reto, privateKey);
				long finCifradoAsimetrico = System.currentTimeMillis();

				flujoSalida.writeUTF(retoCifrado);



				//El servidor extrae la llave sim�trica y responde con un mensaje de confirmaci�n (�ACK�).
				String llaveSimetrica = flujoEntrada.readUTF();
				Key simmetricKey = ec.decryptKeyAsymmetric(llaveSimetrica, privateKey);
				areaTexto.append("\n"+ llaveSimetrica);

				flujoSalida.writeUTF("ACK");

				// TODO  Si el nombre no est� en la tabla el servidor responde con un mensaje de error (�ERROR�). 

				String nombre = flujoEntrada.readUTF();			
				
				areaTexto.append("\n"+ nombre);
				
				Boolean flag = false;
				
				int linea = 0;
				
				 for (int y=0; y < 32; y++) {
					    String nombreTabla = Servidor.matriz[0][y];	
					    
					    if (nombreTabla.equals(nombre)){
					    	flag = true;
					    	linea = y;
					    }
					    
				 }

				if (flag) {

					flujoSalida.writeUTF("ACK");

					// TODO Al recibir la solicitud anterior, el servidor verifica que el nombre de usuario recibido antes y el identificador del
					//paquete est�n en la tabla y correspondan.

					String paquete = flujoEntrada.readUTF();

					areaTexto.append("\n"+ paquete);
					flag = false;
					
					    String numeroPaquete = Servidor.matriz[1][linea];	
					    
					    if (numeroPaquete.equals(paquete)){
					    	flag = true;
					    	
					    }
					
					
					if(flag) {

						flujoSalida.writeUTF("Estado del paquete");

						String confirmacionServidor = flujoEntrada.readUTF();

						areaTexto.append("\n"+ confirmacionServidor);

						//TODO Enviar codigo de resumen

						flujoSalida.writeUTF("Codigo resumen");

					}else {

						areaTexto.append("\n"+ "No encontrado el paquete");
					}





				} else {

					flujoSalida.writeUTF("Error");
				}


				flujoSalida.close();

				//miSocket.close();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}