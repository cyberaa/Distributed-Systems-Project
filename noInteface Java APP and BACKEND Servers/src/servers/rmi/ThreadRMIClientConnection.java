/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servers.rmi;

import clients.rmi.ClientInterface;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objectos.Session;
import objectos.User;

/**
 *
 * @author hevora
 */
public class ThreadRMIClientConnection extends Thread {

    RMI_ServerImpl serverImplementation;

    public ThreadRMIClientConnection(RMI_ServerImpl implementation) {
        this.serverImplementation = implementation;
    }

    @Override
    public void run() {
        while (true) {

            serverImplementation.updateTabelaUsersOnline();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadRMIClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
