
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
    public int[] numeros;
    public String datosServicio;
    
    public void Connect(int broker,int[] numeros){
        this.numeros = numeros;
        String hostName = "192.168.230.149";
        //int portNumber = (int)this.ListaDeBrokers.get(broker);
        int portNumber = 4444;
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Broker: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;
                
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    if(fromUser.toLowerCase().contains("servicio")){
                        packData(numeros);
                        fromUser +=","+datosServicio;
                        out.println(fromUser);
                    }else
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    
    }
    
    public void packData(int[] numeros){
        datosServicio ="";
        
        for (int num : numeros) {
            datosServicio+= num+"|";
        }
        
        System.out.println(datosServicio);
    }
}
