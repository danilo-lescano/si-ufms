import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

public class TCPServer{
    String fn = "";
    int sport, wnd;
    float lp;
    DatagramSocket serverSocket;
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];

	public static void main(String argv[]) throws Exception{
        new TCPServer(argv);
    }

    TCPServer(String argv[]) throws Exception{
        CheckUsage(argv);
        serverSocket = new DatagramSocket(sport);
        while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
            TCPController.addPack(receiveData);
        }
    }

    void CheckUsage(String argv[]){
        if(argv.length > 0 && (argv[0].equals("-h") || argv[0].equals("--help"))){
            System.out.println("OPTIONS:\n\tfn: nome do arquivo a ser recebido e gravado em disco\n\tsport: porta UDP que o servidor deve escutar\n\twnd: tamanho da janela do transmissor e receptor em bytes\n\tlp: probabilidade de um datagrama UDP ser descartado (valor entre 0 e 1)");
            System.exit(0);
        }
        else if(argv.length < 4)
            printUsageAndExit();
        else{
            try{
                lp = Float.parseFloat(argv[argv.length - 1]);
                if(lp > 1) lp = 1;
                if(lp < 0) lp = 0;
                wnd = Integer.parseInt(argv[argv.length - 2]);
                sport = Integer.parseInt(argv[argv.length - 3]);
                for(int i = argv.length - 4; i >= 0; i--)
                    fn = argv[i] + " " + fn;
            }catch(Exception e){
                printUsageAndExit();
            }
        }
        //System.out.println("fn: " + fn + "\n" + "sport: " + sport + "\n" + "wnd: " + wnd + "\n" + "lp: " + lp + "\n");
    }
    void printUsageAndExit(){
        System.out.println("USAGE:\n\tjava TCPServer <fn> <sport> <wnd> <lp>\n\tjava TCPServer -h | --help");
        System.exit(0);
    }
}