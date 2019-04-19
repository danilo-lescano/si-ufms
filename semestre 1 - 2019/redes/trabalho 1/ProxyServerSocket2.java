import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ProxyServerSocket2 extends Thread{
    public static void main(String args[]) throws Exception {
        int c;
        InetAddress a = InetAddress.getByName("pudim.com.br");
        Socket s = new Socket(a.getHostAddress(), 80);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        String str = "GET /pudim.jpg HTTP/1.1\r\nHost: www.pudim.com.br\r\n\r\n";
        byte[] buf = str.getBytes();
        out.write(buf);
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }
        s.close();
    }
}