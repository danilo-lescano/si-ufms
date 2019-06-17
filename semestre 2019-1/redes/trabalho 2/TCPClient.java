import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient{
    private static String fn = "";
    private static InetAddress sip;
    private static int sport;
    private static int wnd;
    private static int rto;
    private static int mss;
    private static boolean dupack;
    private static float lp;

    public static void main(String argv[]){
        CheckUsage(argv);
    }
    private static void CheckUsage(String argv[]){
        if(argv.length > 0 && (argv[0].equals("-h") || argv[0].equals("--help"))){
            System.out.println("OPTIONS:\n\tfn: nome do arquivo a ser enviado\n\tsip: endere¸co IP do servidor\n\tsport: porta UDP do servidor\n\twnd: tamanho da janela do transmissor e receptor em bytes\n\trto: valor inicial de timeout para retransmissao de um segmento em milisegundos\n\tmss: TCP Maximum Segment Size\n\tdupack: deve ser 1 para usar retransmissao via ACKs duplicados e 0 caso contrario\n\tlp: probabilidade de um datagrama UDP ser descartado (valor entre 0 e 1)");
            System.exit(0);
        }
        else if(argv.length < 8)
            printUsageAndExit();
        else{
            try{
                lp = Math.max(0, Math.min(1, Float.parseFloat(argv[argv.length - 1])));
                dupack = Integer.parseInt(argv[argv.length - 2]) == 1 ? true : false;
                mss = Integer.parseInt(argv[argv.length - 3]);
                rto = Integer.parseInt(argv[argv.length - 4]);
                wnd = Integer.parseInt(argv[argv.length - 5]);
                sport = Integer.parseInt(argv[argv.length - 6]);
                sip = InetAddress.getByName(argv[argv.length - 7]);

                for(int i = argv.length - 8; i >= 0; i--)
                    fn = argv[i] + " " + fn;
            }catch(Exception e){
                printUsageAndExit();
            }
        }
        System.out.println("fn: " + fn + "\n" + "sip: " + sip + "\n" + "sport: " + sport + "\n" + "wnd: " + wnd + "\n" + "rto: " + rto + "\n" + "mss: " + mss + "\n" + "dupack: " + dupack + "\n" + "lp: " + lp);
    }
    private static void printUsageAndExit(){
        System.out.println("USAGE:\n\tjava TCPClient <fn> <sip> <sport> <wnd> <rto> <mss> <dupack> <lp>\n\tjava TCPClient -h | --help");
        System.exit(0);
    }
}