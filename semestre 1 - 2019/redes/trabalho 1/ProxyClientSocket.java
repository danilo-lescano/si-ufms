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
            conStatus = true;
        }catch(Exception e) {
            System.out.println("ProxyClientSocket: unable to reach address");
        }
    }

    public byte[] GET(byte[] header_byte){
        try{
            byte[] response;
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
            out.write(header_byte);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = in.read();
            String str = "";
            while (next > -1) {
                //System.out.print((char) next);
                str += (char) next;
                bos.write(next);
                if(in.available() < 1)
                    break;
                next = in.read();
            }
            bos.flush();

            response = bos.toByteArray();
            System.out.println(str);
            bos.close();
            in.close();
            out.close();
            clientSocket.close();
            return response;
        }catch(Exception e) {
            System.out.println("fail");
            return "fail".getBytes();
        }
    }

    public boolean ConectionStatus(){
        return conStatus;
    }
}