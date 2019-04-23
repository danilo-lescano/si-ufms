import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;
import java.util.concurrent.*;

public class ProxyServerSocket extends Thread{
    BufferedReader in;
    OutputStream out;
    Socket connectionSocket;
    SingletonStorage ss;
    Semaphore sem;

    public ProxyServerSocket(Socket conSock){
        connectionSocket = conSock;
        ss = SingletonStorage.get();
        sem = ss.getSemaphore();
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
        try{
            sem.acquire();
        }catch(Exception e) {}
        if(ss.hasData(header_bytes)){
            sem.release();
            String key = ss.getKey(header_bytes);
            System.out.println(key);
            byte[] data = ss.get(key);
            try{
                out.write(data);
            }catch(Exception e) {
                System.out.println("ProxyServerSocket: error on write out byte array1");
            }
        }
        else if(hasHostToken(header_bytes)){
            sem.release();
            ProxyClientSocket cs = new ProxyClientSocket(getHostToken(header_bytes), getPort(header_bytes));

            if(cs.ConectionStatus()){
                cs.writeByteArray(header_bytes);
                try{
                    if(cs.ConectionStatus()){
                        byte[] data = cs.getByteArray();
                        String key = ss.getKey(header_bytes);
                        System.out.println(getHostToken(header_bytes) + key);

                        if(!key.equals("no key"))
                            ss.storeBytes(key, data);
                        out.write(data);
                    }
                }catch(Exception e) {
                    System.out.println("ProxyServerSocket: error on write out byte array2");
                }
            }
        }
        else
            sem.release();
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
        String[] hostToken = tokenizedLine.nextToken().split(":");
        return hostToken[0];
    }
    public int getPort(byte[] data){
        StringTokenizer tokenizedLine = new StringTokenizer((new String(data)).split("\\n")[1]);
        tokenizedLine.nextToken();
        String[] hostToken = tokenizedLine.nextToken().split(":");
        return hostToken.length == 2 ? Integer.parseInt(hostToken[1]) : 80;
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
            //System.out.println(str);
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
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