/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servers.tcp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servers.MasterServer;
/**
 *
 * @author Iris
 */
public class ThreadControlSever extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int portoTCP, portoRMI;

    public ThreadControlSever(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, int portoTCP, int portoRMI) {
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
        this.portoTCP = portoTCP;
        this.portoRMI = portoRMI;


    }

    public void run() {
        boolean value = false;

        String str = "RECORD_SERVER:localhost:" + portoTCP + ":" + portoRMI;
        try {
            oos.writeObject(str);

            boolean stop = false;
            while (!stop) {
                Object obj;

                obj = ois.readObject();

                if (obj == null) {
                    continue;
                }
                handleMessage(obj);
            }


        } catch (UnknownHostException ex) {
            //Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketTimeoutException ex) {
            //System.out.println("Socket exception: ultrapassou o tempo de espera");
        } catch (IOException ex) {
            //Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleMessage(Object obj) {
        if (obj instanceof String) {
            String res = (String) obj;
            if (res.equalsIgnoreCase("Server registered")) {
                MasterServer.registered = true;
            } else if (res.equalsIgnoreCase("Server not registered")) {
                MasterServer.registered = false;
            }
        } 
    }
}
