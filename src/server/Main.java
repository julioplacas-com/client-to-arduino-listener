package server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import utils.Command;

public class Main {
	private static final int PUERTO_ARDUINO = 3000;
	private static final int PUERTO_ESCUCHA = 3001;

	public static void main(String[] args) {
		Compartido compartido = new Compartido();

		Thread tArduino = new Thread(() -> {
			try {
				DatagramSocket serverSocket = new DatagramSocket(PUERTO_ARDUINO);
				byte[] recibidos = new byte[1024];
				byte[] enviados = new byte[1024];
				String cadena;

				while (true) {
					// RECIBO DATAGRAMA
					recibidos = new byte[1024];
					DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
					serverSocket.receive(paqRecibido);

					// DIRECCION ORIGEN
					InetAddress IPOrigen = paqRecibido.getAddress();
					int puerto = paqRecibido.getPort();

					// ENVIO DATAGRAMA AL CLIENTE
					Command command = compartido.getAndClearCommand();
					cadena = command.getCommand();
					enviados = cadena.getBytes();
					DatagramPacket paqEnviado = new DatagramPacket(enviados, enviados.length, IPOrigen, puerto);
					serverSocket.send(paqEnviado);

					if (command == Command.OFF) break;
				}
				serverSocket.close();
				System.out.println("Cerrando puerto arduino ...");
			} catch (Exception e) {
				System.err.println("AAAAA Error puerto arduino AAAAA");
			}
		});

		Thread tCliente = new Thread(() -> {
			try {
				DatagramSocket serverSocket = new DatagramSocket(PUERTO_ESCUCHA);
				byte[] recibidos = new byte[1024];
				String cadena;

				while (true) {
					// RECIBO DATAGRAMA
					recibidos = new byte[1024];
					DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
					serverSocket.receive(paqRecibido);

					cadena = new String(paqRecibido.getData()).trim();
					System.out.println(cadena);

					try { compartido.setCommand(Command.valueOf(cadena)); }
					catch (Exception e) {
						System.err.println("Error parseando: " + cadena);
					}

					if (compartido.getCommand() == Command.OFF) break;
				}
				serverSocket.close();
				System.out.println("Cerrando puerto escucha ...");
			} catch (Exception e) {
				System.err.println("AAAAA Error puerto escucha AAAAA");
			}
		});

		tArduino.start();
		tCliente.start();
	}
}
