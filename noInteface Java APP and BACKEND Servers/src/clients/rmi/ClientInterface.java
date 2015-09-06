/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author hevora
 */
public interface ClientInterface extends Remote{
    public void callback(Object o)throws RemoteException;
    public boolean isClientUp()throws RemoteException;
}
