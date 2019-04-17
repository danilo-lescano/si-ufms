import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class ClientSocket extends Thread{

    public String GET(String header1, String header2, String host){
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