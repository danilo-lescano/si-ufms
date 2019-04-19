import java.io.*;
import java.net.*;
import java.util.*;

public class ProxyClientSocket{
    String header1, header2, host;
    
    public ProxyClientSocket(String header1, String header2, String host){
        this.header1 = header1;
        this.header2 = header2;
        this.host = host;
    }

    public String GET(BufferedReader inFromClient){
        try{
            InetAddress address = InetAddress.getByName(host);
            Socket clientSocket = new Socket(address.getHostAddress(), 80);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromOutsideServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            String stringRequest = header1 + "\r\n" + header2 + "\r\n";
            String stringAwnser;

            String s = "\r\n";
            while (inFromClient.ready()) {
                stringAwnser = inFromClient.readLine();
                stringRequest += stringAwnser + s;
                if(stringAwnser.equals(""))
                    s = "";
            }
            stringRequest += "\r\n";
            outToServer.writeBytes(stringRequest);

            stringAwnser = inFromOutsideServer.readLine();
            s = "\r\n";
            
            while(inFromOutsideServer.ready()) {
                stringRequest = inFromOutsideServer.readLine();
                stringAwnser += stringRequest + s;
                if(stringAwnser.equals(""))
                    s = "";
            }
            System.out.println(stringAwnser);

            clientSocket.close();
            return stringAwnser;
        }catch(Exception e) {
            System.out.println("fail");
            return "fail";
        }
    }    
}