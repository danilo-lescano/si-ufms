import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ProxyServerSocket extends Thread{
    BufferedReader inFromClient;
    OutputStream outToClient;
    Socket connectionSocket;

    public ProxyServerSocket(Socket conSock){
        try{
            connectionSocket = conSock;
            inFromClient  = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = connectionSocket.getOutputStream();
            start();
        }
        catch(Exception e) {
        //  Block of code to handle errors
        }
    }
    public void run(){
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = inFromClient.read();
            while(next > -1) {
                bos.write(next);
                if(!inFromClient.ready())
                    break;
                next = inFromClient.read();
            }
            bos.flush();

            byte[] header_bytes = bos.toByteArray();
            String getHost = (new String(header_bytes)).split("\\n")[1];

            StringTokenizer tokenizedLine = new StringTokenizer(getHost);
            if(tokenizedLine.nextToken().equals("Host:")){
                ProxyClientSocket cs = new ProxyClientSocket(tokenizedLine.nextToken());
                outToClient.write(cs.GET(header_bytes));
            }
            connectionSocket.close();
        }
        catch(Exception e) {
        //  Block of code to handle errors
        }
    }
}