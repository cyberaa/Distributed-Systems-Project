/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import servers.tcp.TCPSERVER;
import servers.tcp.ThreadControlSever;

/**
 *
 * @author jaimecruz
 */
public class MasterServer {

    public static Socket s;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static boolean registered;
    
    public static void main(String[] args) throws Exception {
        int serverPort = 6790;
        s = new Socket("localhost", serverPort);
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
        // regista o servidor com os portos TCP e RMI
        recordServer(5000, 3000);
        Thread.sleep(1000);
        System.out.println("");
        //if (recordServer(5000,3000)) {
        if (registered) {
            System.out.println("Servidor registado");
//            new ServerRMI(session, 3000);
//            new ServerSocket(session, 5000, 5020, 3000);
            new TCPSERVER(5000, 5020);
        } else {
            System.out.println("ERROR: Não foi possivel resgistar o servidor!\nCAUSE: Esse servidor já está a correr!\nSOLUTION: Verifique se nenhum porto coincide com os dos servidores que já estão a correr...");
        }


    }

    private static void recordServer(int portoTCP, int portoRMI) {

        ThreadControlSever threadControlSever = new ThreadControlSever(s, oos, ois, portoTCP, portoRMI);
        threadControlSever.start();

    }
}
