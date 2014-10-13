
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Romario
 */
public class Cliente {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
         Scanner scn2 = new Scanner(System.in);
        int numeros[] = new int[10];
        ProxyCliente proxy = new ProxyCliente();
        int i=0;
        
        //proxy.Connect(1);
        int a=scn.nextInt();
        
        while(a!=0){
            numeros[i] = a;
            a=scn.nextInt();
            i++;
        }
        proxy.Connect(a,numeros);
        //proxy.peticionDeServicio(null, numeros);
    }
}
