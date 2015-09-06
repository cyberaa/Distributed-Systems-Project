/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import objectos.operacoes.Notification;

/**
 *
 * @author hevora
 */
public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface{

    public ClientInterfaceImpl() throws RemoteException {
        super();
    }

    
    @Override
    public void callback(Object obj) throws RemoteException {
        if (obj instanceof Notification) {
            Notification at = (Notification)obj;
            System.err.println("!!!"+at.getTexto()+"!!!");
        } else if (obj instanceof String) {
            String result = (String) obj;
            System.err.println("!!!"+result+"!!!");
        }
    }

    @Override
    public boolean isClientUp() throws RemoteException {
        return true;
    }
    
}
