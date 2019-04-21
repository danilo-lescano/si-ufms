import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.concurrent.*;

public class ProxyServerSocket extends Thread{
    BufferedReader in;
    OutputStream out;
    Socket connectionSocket;

    public ProxyServerSocket(Socket conSock){
        connectionSocket = conSock;
        try{
            in  = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            out = connectionSocket.getOutputStream();
        }catch(Exception e) {
            System.out.println("ProxyServerSocket: input output stream error");
            return;
        }
        start();
    }
    public void run(){
        byte[] header_bytes = getByteArray();
        if(hasHostToken(header_bytes)){
            ProxyClientSocket cs = new ProxyClientSocket(getHostToken(header_bytes));
            try{
                out.write(cs.GET(header_bytes));
            }catch(Exception e) {
                System.out.println("ProxyServerSocket: error on write out byte array");
            }
        }
        closeConnection();
    }

    public boolean hasHostToken(byte[] data){
        String[] headerStrs = (new String(data)).split("\\n");
        if(headerStrs.length < 2)
            return false;
        StringTokenizer tokenizedLine = new StringTokenizer((new String(data)).split("\\n")[1]);
        if(tokenizedLine.nextToken().equals("Host:"))
            return true;
        return false;
    }
    public String getHostToken(byte[] data){
        StringTokenizer tokenizedLine = new StringTokenizer((new String(data)).split("\\n")[1]);
        tokenizedLine.nextToken();
        return tokenizedLine.nextToken();
    }

    public byte[] getByteArray(){
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = in.read();
            String str = "";
            while(next > -1) {
                str += (char) next;
                bos.write(next);
                if(!in.ready())
                    break;
                next = in.read();
            }
            bos.flush();
            System.out.println(str);
            
            return bos.toByteArray();
        }catch(Exception e) {
            System.out.println("ProxyServerSocket: error on get byte array");
            return "".getBytes();
        }
    }

    public void closeConnection(){
        try{
            in.close();
        }catch(Exception e) {
            System.out.println("ProxyServerSocket: error on (in) close");
        }
        try{
            out.close();
        }catch(Exception e) {
            System.out.println("ProxyServerSocket: error on (out) close");
        }
        try{
            connectionSocket.close();
        }catch(Exception e) {
            System.out.println("ProxyServerSocket: error on (sock) close");
        }
    }
}