import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ProxyHTTPServer{
	public static void main(String argv[]) throws Exception{
		if(argv.length != 2)
			System.out.println("print usage");
		float tamanhoEmMB = Integer.parseInt(argv[1]);
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
		InetAddress a = welcomeSocket.getInetAddress();

		List<ConnectionSocket> listSockets = new ArrayList<ConnectionSocket>();

		System.out.println(a.getHostAddress() + "\n" + a.getHostName()); 	
		System.out.println("Address: " + a.getHostAddress());

		while(true) {
			ConnectionSocket connectionSocket = new ConnectionSocket(welcomeSocket.accept());
			listSockets.add(connectionSocket);
		}
	}
}