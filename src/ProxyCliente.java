import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class ProxyCliente {

    ArrayList<Candidatos> candidatos;
    public String datosServicio;

    public void Connect(String clienteDice, ArrayList candidatos) {
        this.candidatos = candidatos;
        String hostNameBroker = "127.0.0.1";
        int portNumberBroker = 4444;
        try (
                Socket kkSocket = new Socket(hostNameBroker, portNumberBroker);
                PrintWriter aBroker = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader deBroker = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));) {
            BufferedReader stdIn
                    = new BufferedReader(new InputStreamReader(System.in));
            String fromBroker;
            String fromClient;

            while ((fromBroker = deBroker.readLine().toLowerCase()) != null) {
                if(fromBroker.contains("terminar")){
                    System.out.println("Broker: " + fromBroker);
                    break;
                }
                System.out.println("Broker: " + fromBroker);
                fromClient = clienteDice;
                packData();
                fromClient += "," + datosServicio;
                aBroker.println(fromClient);
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostNameBroker);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostNameBroker);
            System.exit(1);
        }

    }

    public void packData() {
        datosServicio = "";

        for (Candidatos cand : candidatos) {
            String dato = cand.getNombre() + "|" + cand.getVotos() + "%";
            datosServicio += dato;
        }

        System.out.println(datosServicio);
    }
}
