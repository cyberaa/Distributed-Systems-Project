/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servers.tcp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iris
 */
public class ThreadLimpaBuffer extends Thread{

    private boolean stop;
    private TCPSERVER tcpserver;
    public ThreadLimpaBuffer( TCPSERVER tcpserver) {
        this.stop = false;
        this.tcpserver = tcpserver;
    }
    @Override
    public void run(){
        while(!stop){
            try {
                Thread.sleep(5000);
                tcpserver.esvaziarBuffer();
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
