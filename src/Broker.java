import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Romario
 */
public class Broker {

    private static final int WAITING = 0;
    private static final int SENDTOPROXYSERVER = 1;
    private static final int SENDTOPROXYCLIENT = 2;
    private static final int SENTBROKERS = 3;
    public int BrokerSeleccionado;

    ArrayList<Servers> ListaDeBrokers = new ArrayList();

    public void abrirServer() {
        ListaDeBrokers.add(new Servers("192.168.230.149", 4444));
        ListaDeBrokers.add(new Servers("192.168.230.150", 4444));
        ListaDeBrokers.add(new Servers("192.168.230.151", 4444));
        int portNumber = 4444;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));) {
            
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
            out.println("Te has conectado satisfactoriamente");
            String inputLine, outputLine;

            
            // Initiate conversation with client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Cliente: " + inputLine);
                if ((inputLine.equalsIgnoreCase("listar"))) {
                    listarBrokers(out);
                } else if (inputLine.toLowerCase().contains("broker")) {
                    conectarABroker(inputLine, out);
                }else if (inputLine.toLowerCase().contains("servicio")) {
                    servicio(inputLine, out);
                }else if (inputLine.toLowerCase().contains("agregar")) {
                    agregarServidor(inputLine, out);
                } else {
                    out.println("Comando no encontrado");
                }

            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public void servicio(String input, PrintWriter outClient){
        String hostName = ListaDeBrokers.get(BrokerSeleccionado).getIp();
        //int portNumber = (int)this.ListaDeBrokers.get(broker);
        int portNumber = ListaDeBrokers.get(BrokerSeleccionado).getPort();
        
        String servicio = input.split(",")[0];
        String datos = input.split(",")[1];
        
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter server = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader inServer = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer = null;

            
            server.println(input);
            fromServer = inServer.readLine();
            System.out.println("Server: " + fromServer); 
            server.println(input);
            fromServer = inServer.readLine();
            outClient.println(fromServer);
            
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about hostServer " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
    public void agregarServidor(String str, PrintWriter out) {
        String[] partida = str.split(" ");
        if (partida.length != 3) {
            out.println("Escribir> agregar IP PORT");
        } else {
            out.println("Servidor agregado> " + partida[1]);
        }
    }

    public void conectarABroker(String str, PrintWriter out) {
        String[] partida = str.split(" ");
        if (partida.length != 2) {
            out.println("Escribir> broker No.Broker");
        } else {
            BrokerSeleccionado = Integer.parseInt(partida[1]);
            out.println("Broker elegido> " + partida[1]);
        }
    }

    public void listarBrokers(PrintWriter out) {
        String outputLine;
        outputLine = ListaDeBrokers.toString();
        out.println(outputLine);
    }

    public static void main(String[] args) {
        Broker broker = new Broker();
        broker.abrirServer();
    }
}
