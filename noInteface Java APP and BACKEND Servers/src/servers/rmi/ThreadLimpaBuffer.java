/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servers.rmi;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iris
 */
public class ThreadLimpaBuffer extends Thread{

    private boolean stop;
    private RMI_ServerImpl rMI_ServerImpl;
    public ThreadLimpaBuffer( RMI_ServerImpl rMI_ServerImpl) {
        this.stop = false;
        this.rMI_ServerImpl = rMI_ServerImpl;
    }
    @Override
    public void run(){
        while(!stop){
            try {
                Thread.sleep(5000);
                rMI_ServerImpl.esvaziarBuffer();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadLimpaBuffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
}
