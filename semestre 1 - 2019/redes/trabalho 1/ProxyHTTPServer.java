import java.io.*;
import java.net.*;
import java.util.*;

public class ProxyHTTPServer{
	public static void main(String argv[]) throws Exception{
		if(argv.length != 1)
			System.out.println("DEU RUIM");

        GetInetAddress(argv[0]);
	}


	private static InetAddress GetInetAddress(String hostname){
		InetAddress a = null;
		try { 
			a = InetAddress.getByName(hostname);
			System.out.println(hostname + ": " + a.getHostAddress()); 	
		} catch (UnknownHostException e) {
			System.out.println("No address found for " + hostname); 
		}
		return a;
	}
}