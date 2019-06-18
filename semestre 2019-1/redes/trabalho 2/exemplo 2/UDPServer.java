import java.io.*;
import java.net.*;

public class UDPServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876); //porta
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		String sentence;

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
		
		sentence = new String(receivePacket.getData());

		InetAddress IPAddress = receivePacket.getAddress();

		DatagramPacket sendPacket = new DatagramPacket(
			sentence.toUpperCase().getBytes(),
			sendData.length,
			IPAddress,
			receivePacket.getPort());

		serverSocket.send(sendPacket);

		System.out.println(sentence.toUpperCase());
	}
}