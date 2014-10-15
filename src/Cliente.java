
import java.util.ArrayList;
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
    
    static ArrayList<Candidatos> candidatos = new ArrayList();
    
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        
        ProxyCliente proxyCliente = new ProxyCliente();
        int i=0;
        
        //proxy.Connect(1);
        
        
        candidatos.add(new Candidatos("romario"));
        candidatos.add(new Candidatos("alejandro"));
        candidatos.add(new Candidatos("eduardo"));
        String clienteDice;
        System.out.println("Ingrese el el numero para votar por:"
                + "\n1.- Votar"
                + "\n2.- Ver Votos"
                + "\n3.- Agregar Candidato "
                + "\nPara enviar los datos escriba Enviar seguido del servicio "
                + "\n Ejemplo: Enviar Barras, Enviar Pastel, Enviar Tabla");
        while(!(clienteDice= scn.next()).toLowerCase().contains("enviar")){
            
            
            switch(Integer.parseInt(clienteDice)){
                case 1: votar();
                    break;
                case 2: verVotos();
                    break;
                case 3: agregarCandidato();
                    break;
            }
            System.out.println("Ingrese el el numero para votar por:"
                + "\n1.- Votar"
                + "\n2.- Ver Votos "
                    + "\n3.- Agregar Candidato");
        }
        System.out.println(clienteDice);
        proxyCliente.Connect(clienteDice,candidatos);
        //proxy.peticionDeServicio(null, numeros);
    }
    
    public static void agregarCandidato(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Ingresa el nombre");
        candidatos.add(new Candidatos(scn.next()));
    }
    
    public static void verVotos(){
        for(Candidatos cand: candidatos){
            System.out.println(cand.getNombre() + " >> " + cand.getVotos());
        }
    }
    
    public static void votar(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Ingresa el nombre del candidato");
        String a = scn.next().toLowerCase();
        for(Candidatos cand: candidatos){
            if(cand.getNombre().equals(a)){
                cand.incrementarVotos();
            }
        }
    }
}
