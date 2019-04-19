import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ProxyServerSocket extends Thread{
    Socket connectionSocket;
    String clientSentence;
    String capitalizedSentence;
    BufferedReader inFromClient;
    DataOutputStream outToClient;

    public ProxyServerSocket(Socket connectionSocket){
        try{
            this.connectionSocket = connectionSocket;

            inFromClient  = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
            outToClient = new DataOutputStream(this.connectionSocket.getOutputStream());

            start();
        }
        catch(Exception e) {
        //  Block of code to handle errors
        }
    }
    public void run(){
        try{
    		String getHeader = inFromClient.readLine();
            String getHost;

		    StringTokenizer tokenizedLine = new StringTokenizer(getHeader);

		    if(tokenizedLine.nextToken().equals("GET")){
                getHost = inFromClient.readLine();

                tokenizedLine = new StringTokenizer(getHost);
    		    if(tokenizedLine.nextToken().equals("Host:")){
                    ProxyClientSocket cs = new ProxyClientSocket(getHeader, getHost, tokenizedLine.nextToken());
                    outToClient.writeBytes(cs.GET(inFromClient));
                }
            }
            connectionSocket.close();
        }
        catch(Exception e) {
        //  Block of code to handle errors
        }
    }
}