import java.io.*; 
import java.net.*; 

class TCPClient { 
	public static void main(String argv[]) throws Exception { 
		String sentence; 
		String modifiedSentence; 

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		InetAddress a = InetAddress.getByName("pudim.com.br");
		System.out.println(a.getHostAddress());

		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
			
		sentence = inFromUser.readLine(); 
		outToServer.writeBytes(sentence + '\n');
		while((modifiedSentence = inFromServer.readLine()) != null){
			System.out.println("FROM SERVER: " + modifiedSentence);
		}
		clientSocket.close();
	} 
}