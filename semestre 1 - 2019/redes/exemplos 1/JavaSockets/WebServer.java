import java.io.*;
import java.net.*;
import java.util.*;

class WebServer {
    public static void main(String[] args) throws Exception {
        // création de la socket
        int port = 8005;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Serveur lance sur le port : " + port);
    
        // repeatedly wait for connections, and process
        while (true) {
            // on reste bloqué sur l'attente d'une demande client
            Socket clientSocket = serverSocket.accept();
            System.err.println("Nouveau client connecte");
    
            // on ouvre un flux de converation
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    
            // chaque fois qu'une donnée est lue sur le réseau on la renvoi sur
            // le flux d'écriture.
            // la donnée lue est donc retournée exactement au même client.
            String s;
            while (in.ready()) {
                s = in.readLine();
                System.out.println(s);
            }
    
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Content-Length: 57\r\n");
            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.write("<TITLE>Exemple</TITLE>");
            out.write("<P>Ceci est une page d'exemple.</P>");
    
            // on ferme les flux.
            System.err.println("Connexion avec le client terminee");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
