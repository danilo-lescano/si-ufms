import java.io.*;
import java.net.*;
import java.util.*;

public class ProxyClientSocket{
    Socket clientSocket;
    InputStream in;
    OutputStream out;

    boolean conStatus = false;

    public ProxyClientSocket(String host, int port){
        try{
            InetAddress address = InetAddress.getByName(host);
            clientSocket = new Socket(address.getHostAddress(), port);
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: unable to reach address");
            closeConnection();
            return;
        }
        try{
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: input output stream error");
            closeConnection();
            return;
        }
        conStatus = true;
    }

    public boolean writeByteArray(byte[] data){
        try{
            out.write(data);
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: output error");
            closeConnection();
            return false;
        }
        return true;
    }

    public byte[] getByteArray(){
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = in.read();
            String str = "";
            while(next > -1) {
                str += (char) next;
                bos.write(next);
                if(in.available() < 1)
                    break;
                next = in.read();
            }
            bos.flush();
            //System.out.println(str);
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: error on get byte array");
            closeConnection();
            return "".getBytes();
        }
    }

    public boolean ConectionStatus(){
        return conStatus;
    }

    public void closeConnection(){
        try{
            if(in != null)
                in.close();
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: error on (in) close");
        }
        try{
            if(out != null)
                out.close();
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: error on (out) close");
        }
        try{
            if(clientSocket != null)
                clientSocket.close();
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: error on (sock) close");
        }
        conStatus = false;
    }
}