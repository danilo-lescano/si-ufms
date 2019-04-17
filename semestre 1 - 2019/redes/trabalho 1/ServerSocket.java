import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ServerSocket extends Thread{
    Socket connectionSocket;
    InetAddress b;
    String clientSentence;
    String capitalizedSentence;
    BufferedReader inFromClient;
    DataOutputStream outToClient;

    public ServerSocket(Socket connectionSocket){
        try{
            this.connectionSocket = connectionSocket;
            b = connectionSocket.getInetAddress();

            System.out.println("Address: " + b.getHostAddress());

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
		    System.out.println(getHeader);

		    if(tokenizedLine.nextToken().equals("GET")){
                getHost = inFromClient.readLine();
    		    System.out.println(getHost);

                tokenizedLine = new StringTokenizer(getHost);
    		    if(tokenizedLine.nextToken().equals("Host:"))
                    outToClient.writeBytes(xxx(getHeader, getHost, tokenizedLine.nextToken()));
            }
            connectionSocket.close();
        }
        catch(Exception e) {
        //  Block of code to handle errors
        }
    }

    public String xxx(String header1, String header2, String host){
        try{
            InetAddress address = InetAddress.getByName(host);
            Socket clientSocket = new Socket(address.getHostAddress(), 80);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromOutsideServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            String stringRequest = header1 + "\r\n" + header2 + "\r\n";
            String stringAwnser;

            //while((stringAwnser = inFromOutsideServer.readLine()) != null)
                //stringRequest += stringAwnser + "\r\n";
            while (true) {
                stringAwnser = inFromClient.readLine();
                if(stringAwnser.equals(""))
                    break;
                stringRequest += stringAwnser + "\r\n";
            }
            stringRequest += "\r\n";

            outToServer.writeBytes(stringRequest);

            stringAwnser = "";
            while((stringRequest = inFromOutsideServer.readLine()) != null)
                stringAwnser += stringRequest + "\r\n";
            stringAwnser += "\r\n";
            clientSocket.close();
            return stringAwnser;
        }catch(Exception e) {
            System.out.println("fail");
            return "fail";
        }
    }
}