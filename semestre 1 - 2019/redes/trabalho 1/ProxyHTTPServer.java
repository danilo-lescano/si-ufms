import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ProxyHTTPServer{
	public static void main(String argv[]){
		if(argv.length != 2){
			System.out.println("print usage");
			return;
		}
		ServerSocket welcomeSocket;
        try{
			welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
			WelcomeMessage(welcomeSocket);
		}catch(Exception e) {
			return;
		}		
		float tamanhoEmMB = Integer.parseInt(argv[1]);
		List<ProxyServerSocket> listSockets = new ArrayList<ProxyServerSocket>();


		while(true) {
			Socket conSock;
			try{
				conSock = welcomeSocket.accept();
			}catch(Exception e) {
				continue;
			}
			ProxyServerSocket proxyServerSocket = new ProxyServerSocket(conSock);
			listSockets.add(proxyServerSocket);
		}
	}

	private static void WelcomeMessage(ServerSocket welcomeSocket){
		InetAddress a = welcomeSocket.getInetAddress();
		System.out.println("Hello. Proxy listen on the following address " + a.getHostAddress());
	}
}