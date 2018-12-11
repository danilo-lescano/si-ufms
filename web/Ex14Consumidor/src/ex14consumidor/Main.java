/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex14consumidor;

/**
 *
 * @author 201319070221
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("O resultado é: " + soma(30, 45));
    }

    private static int soma(int a, int b) {
        ex14consumidor.CalculoBasico_Service service = new ex14consumidor.CalculoBasico_Service();
        ex14consumidor.CalculoBasico port = service.getCalculoBasicoPort();
        return port.soma(a, b);
    }
    
}
