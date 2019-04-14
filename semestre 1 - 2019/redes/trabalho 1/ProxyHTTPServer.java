import java.io.*;
import java.net.*;
import java.util.*;

public class ProxyHTTPServer{
	public static void main(String argv[]) throws Exception{
		if(argv.length != 2)
			System.out.println("print usage");
		String clientSentence; 
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
		InetAddress a = welcomeSocket.getInetAddress();

		float tamanhoEmMB = Integer.parseInt(argv[1]);
		//receber porta e tamanho
		System.out.println(a.getHostAddress() + "\n" + a.getHostName()); 	
		
		System.out.println("Address: " + a.getHostAddress());
		
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			InetAddress b = connectionSocket.getInetAddress();
			
			System.out.println("Address: " + b.getHostAddress());
			
			BufferedReader inFromClient  = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			while (true) {
				clientSentence = inFromClient.readLine();
				if (clientSentence == null)
					break;
				if (clientSentence.equals("exit"))
					break;
				System.out.println(clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
			}
			connectionSocket.close();
		}

        //GetInetAddress(argv[0]);
	}

	/*private static InetAddress GetInetAddress(String hostname){
		InetAddress a = null;
		try { 
			a = InetAddress.getByName(hostname);
			System.out.println(hostname + ": " + a.getHostAddress() + "\n" + a.getHostName()); 	
		} catch (UnknownHostException e) {
			System.out.println("No address found for " + hostname); 
		}
		return a;
	}*/
}