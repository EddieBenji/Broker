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
    static int id=4;
    static ProxyCliente proxyCliente;
    public static void principal() {
        Scanner scn = new Scanner(System.in);

        proxyCliente = new ProxyCliente();

       
        String clienteDice;
        System.out.println("\n\nIngrese el el numero de lo que desea realizar:"
                + "\n1.- Votar"
                + "\n2.- Ver Votos"
                + "\n3.- Agregar Candidato "
                + "\n4.- Ver opciones"
                + "\n5.- Escribir comando "
                + "\nEscriba salir para terminar");
                
       try{
           System.out.print(">> ");
           while (!(clienteDice = scn.nextLine().toLowerCase()).contains("salir")) {
            
            String opcion = clienteDice;
            
            switch (opcion) {
                case "1":
                    votar();
                    break;
                case "2":
                    verVotos();
                    break;
                case "3":
                    agregarCandidato();
                    break;
                case "4":
                    System.out.println("Ingrese el numero de lo que desea realizar:"
                            + "\n1.- Votar"
                            + "\n2.- Ver Votos"
                            + "\n3.- Agregar Candidato "
                            + "\n4.- Ver opciones"
                            + "\n5.- Escribir comando");
                    break;
                case "5": 
                    //clienteDice= scn.nextLine().toLowerCase();
                    proxyCliente.Connect(clienteDice, candidatos);
                    break;
                default: System.out.println("Opción no válida");
                    break;
            }
            System.out.print(">> ");
        }
        //System.out.println(clienteDice);
        
        //proxy.peticionDeServicio(null, numeros);
    }  catch (NumberFormatException nex){
                System.out.println("Opcion no válida");
                principal();
            }
    }
    
    public static void main(String[] args) {
        inicializarCandidatos();
        principal();
    }
    
    public static void inicializarCandidatos(){
        candidatos.add(new Candidatos(1,"romario"));
        candidatos.add(new Candidatos(2,"alejandro"));
        candidatos.add(new Candidatos(3,"eduardo"));
    }
    public static void agregarCandidato() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Ingresa el nombre");
        candidatos.add(new Candidatos(id,scn.nextLine()));
        id++;
    }

    public static void verVotos() {
        for (Candidatos cand : candidatos) {
            System.out.println("Id: "+ cand.getId() + "  "+cand.getNombre() + " >> " + cand.getVotos());
        }
    }

    public static void votar() {
        boolean encontrado= false;
        Scanner scn = new Scanner(System.in);
        System.out.println("Ingresa el nombre del candidato");
        String a = scn.nextLine().toLowerCase();
        for (Candidatos cand : candidatos) {
            if (cand.getNombre().equals(a)) {
                cand.incrementarVotos();
                encontrado= true;
            }
        }
        if(!encontrado)
            System.out.println("Candidato no encontrado");
    }
}
