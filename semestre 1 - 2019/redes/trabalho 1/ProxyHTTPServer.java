import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ProxyHTTPServer{
	public static void main(String argv[]) throws Exception{
		if(argv.length != 2)
			System.out.println("print usage");
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
		float tamanhoEmMB = Integer.parseInt(argv[1]);
		List<ProxyServerSocket> listSockets = new ArrayList<ProxyServerSocket>();

		WelcomeMessage(welcomeSocket);

		while(true) {
			ProxyServerSocket proxyServerSocket = new ProxyServerSocket(welcomeSocket.accept());
			listSockets.add(proxyServerSocket);
		}
	}

	private static void WelcomeMessage(ServerSocket welcomeSocket){
		InetAddress a = welcomeSocket.getInetAddress();
		System.out.println("Hello. Proxy listen on the following address " + a.getHostAddress());
	}
}