import java.io.*;
import java.net.*;
import java.util.*;

public class ProxyClientSocket{
    Socket clientSocket;
    InputStream in;
    OutputStream out;

    public ProxyClientSocket(String host){
        try{
            InetAddress address = InetAddress.getByName(host);
            clientSocket = new Socket(address.getHostAddress(), 80);
        }catch(Exception e) {
            System.out.println("unable to reach address");
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
            while (next > -1) {
                //System.out.print((char) next);
                bos.write(next);
                if(in.available() < 1)
                    break;
                next = in.read();
            }
            bos.flush();

            response = bos.toByteArray();
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
}