import java.io.*; 
import java.net.*; 

class TCPClient { 
	public static void main(String argv[]) throws Exception { 
		String sentence; 
		String modifiedSentence;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		InetAddress a = InetAddress.getByName("pudim.com.br");

		//Socket clientSocket = new Socket(a.getHostAddress(), 80);
		Socket clientSocket = new Socket("localhost", 8005);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
			
		sentence = "GET / HTTP/1.1\r\n";//inFromUser.readLine(); 
		sentence += "Host: www.pudim.com.br\r\n";//inFromUser.readLine(); 
		sentence += "\r\n";
		outToServer.writeBytes(sentence);
		outToServer.flush();

		while((modifiedSentence = inFromServer.readLine()) != null){
			System.out.println(modifiedSentence);
		}
		clientSocket.close();
	} 
}