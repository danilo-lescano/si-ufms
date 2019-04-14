import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ConnectionSocket extends Thread{
    Socket connectionSocket;
    InetAddress b;
    String clientSentence;
    String capitalizedSentence;
    BufferedReader inFromClient;
    DataOutputStream outToClient;

    public ConnectionSocket(Socket connectionSocket){
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
        catch(Exception e) {
        //  Block of code to handle errors
        }
    }
}