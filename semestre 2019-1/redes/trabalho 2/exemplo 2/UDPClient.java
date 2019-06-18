import java.io.*;
import java.net.*;

public class UDPClient {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		int porta = 9876;
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] dados = new byte[1024];

		System.out.println("Digite o texto a ser enviado ao servidor: ");
		dados = (inFromUser.readLine()).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(dados, dados.length, IPAddress, porta);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(dados, dados.length);
		clientSocket.receive(receivePacket);
		System.out.println((new String(receivePacket.getData())));
		clientSocket.close();
	}
}