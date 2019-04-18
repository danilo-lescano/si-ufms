import java.io.*;
import java.util.*;

public class MMC{
	public static void main(String argv[]) throws Exception{
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while(n > 0){
            int dividendo = 2;
            int n_dividido = n;
            String ultimaLinha = "1\t| ";
            int dividendoContador = 0;
            while(n_dividido > 1){
                if(n_dividido % dividendo == 0){
                    System.out.println(n_dividido + "\t| " + dividendo);
                    n_dividido /= dividendo;
                    dividendoContador++;
                    if(n_dividido <= 1)
                        ultimaLinha += dividendo + "(" + dividendoContador + ") ";
                } else{
                    if(dividendoContador > 0)
                        ultimaLinha += dividendo + "(" + dividendoContador + ") ";
                    dividendo++;
                    dividendoContador = 0;
                }
            }
            System.out.println(ultimaLinha);
            n = sc.nextInt();
        }
    }
}