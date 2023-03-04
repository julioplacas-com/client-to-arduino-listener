package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
	private static final String SERVER_DOMAIN = "arduino.julioplacas.com";
	private static final int PUERTO_ESCUCHA = 3001;

	public static void main(String[] args) throws Exception {
		InetAddress destino = InetAddress.getByName(SERVER_DOMAIN);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean running = true;
		Option[] options = Option.values();
		while (running) {
			for (int i = 0; i < options.length; i++)
				System.out.println(i + ". " + options[i].text);
			
			Option option;
			try {
				option = options[Integer.valueOf(in.readLine().trim())];
				if (option == Option.APAGAR_CLIENTE) {
					System.out.println("Apagando cliente ...");
					running = false;
				} else {
					String s = option.command.name();
					DatagramSocket socket = new DatagramSocket();
					socket.send(new DatagramPacket(s.getBytes(), s.length(), destino, PUERTO_ESCUCHA));
					socket.close();
					if (option == Option.APAGAR_ARDUINO) {
						System.out.println("Apagando arduino ...");
						running = false;
					}
				}
			}
			catch (Exception e) { System.err.println("Opcion inexistente\n"); }
		}
	}
}
