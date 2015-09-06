/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servers.dns;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Iris
 */
public class ServerServices extends Thread {

    private Socket servertSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean stop;
    
    public ServerServices(Socket aServerSocket) {
        this.servertSocket = aServerSocket;
        this.stop = false;
        connect();

    }
    @Override
    public void run() {
        execute();
    }

    private void execute() {
        while (!stop) {
            try {
                Object obj;
                obj = ois.readObject();

                if (obj == null) {
                    continue;
                }
                handleOperations(obj);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EOFException e) {
                //stop = true;
            } catch (IOException e) {
                
                DnsCenter.updateTable(ois);
                stop = true;
            }
        }
    }

    private void connect() {
        try {
            ois = new ObjectInputStream(servertSocket.getInputStream());
            oos = new ObjectOutputStream(servertSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("ERROR:Connection Failed:" + e.getMessage());
        }
    }

    private void handleOperations(Object obj) {
        if (obj instanceof String) {
            String msg = (String) obj;
            if (DnsCenter.recordServer(msg.trim(), ois, oos)) {
                try {
                    oos.writeObject("Server registered");
                    //oos.writeObject(this.session);
                } catch (IOException ex) {
                    Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    oos.writeObject("Server not registered");
                } catch (IOException ex) {
                    Logger.getLogger(ServerServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

