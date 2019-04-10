import java.io.*;
import java.net.*;
import java.util.*;

public class ProxyHTTPServer{
	public static void main(String argv[]) throws Exception{
		if(argv.length != 2)
			System.out.println("print usage");

		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
		InetAddress a = welcomeSocket.getInetAddress();
		//receber porta e tamanho
		System.out.println(a.getHostAddress() + "\n" + a.getHostName()); 	
		

		
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