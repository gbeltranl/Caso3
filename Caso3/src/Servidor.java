import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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



		MarcoServidor miMarco = new MarcoServidor();
		
		miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
}
class MarcoServidor extends JFrame implements Runnable{
	public MarcoServidor() {
		
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

		try {
			ServerSocket servidor = new ServerSocket(5001);
			
			while(true) {
			
			Socket miSocket = servidor.accept();
			
			DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());
		
			
			DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());
	
			String mensajeTexto = flujoEntrada.readUTF();
			
			areaTexto.append("\n"+ mensajeTexto);
			
			
			
			// Envío de mensaje ACK
			flujoSalida.writeUTF("ACK");
			//
		
			// Llegada de mensaje de 24 digitos
			String Paso2 = flujoEntrada.readUTF();
			
			areaTexto.append("\n"+ Paso2);
			
			// TODO Cifrar reto con llave privada y enviar al cliente
			
			flujoSalida.writeUTF("Reto Cifrado");
			
			
			
			// TODO El servidor extrae la llave simétrica y responde con un mensaje de confirmación (¨ACK¨).
			String llaveSimetrica = flujoEntrada.readUTF();
			
			areaTexto.append("\n"+ llaveSimetrica);
			
			flujoSalida.writeUTF("ACK");
			
			// TODO  Si el nombre no está en la tabla el servidor responde con un mensaje de error (¨ERROR¨). 
			
			String nombre = flujoEntrada.readUTF();
			
			areaTexto.append("\n"+ nombre);
			
			if (true) {
				
				flujoSalida.writeUTF("ACK");
				
				// TODO Al recibir la solicitud anterior, el servidor verifica que el nombre de usuario recibido antes y el identificador del
				//paquete estén en la tabla y correspondan.
				
				String paquete = flujoEntrada.readUTF();
				
				areaTexto.append("\n"+ paquete);
				
				if(true) {
					
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