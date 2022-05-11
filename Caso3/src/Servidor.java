import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
	private String nombreCliente;
	private long identificadorPaquete;
	private String estadoPaquete;
	
	public static void main(String[] args) {
	
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
		
		miLamina.add(areaTexto,BorderLayout.CENTER);
		
		add(miLamina);
		
		setVisible(true);
		
		Thread miHilo = new Thread(this);
		
		miHilo.start();
				
	}
	
	private JTextArea areaTexto;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("Estoy a la escucha");
		
		try {
			ServerSocket servidor = new ServerSocket(5000);
			
			while(true) {
			
			Socket miSocket = servidor.accept();
			
			DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());
		
			
			DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());

			
			String mensajeTexto = flujoEntrada.readUTF();
			
			areaTexto.append("\n"+ mensajeTexto);
			
			
			
			//
			flujoSalida.writeUTF("Recibido");
			//
		
			flujoSalida.close();
			
			//miSocket.close();
			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	
	
	
	
	
	


