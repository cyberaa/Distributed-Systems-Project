/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iris
 */
public class ThreadLimpaBuffer extends Thread{

    private boolean stop;
    private TCPClient tcpClient;
    private boolean running;
    public ThreadLimpaBuffer( TCPClient tcpClient) {
        this.stop = false;
        this.running = false;
        this.tcpClient = tcpClient;
    }
    @Override
    public void run(){
        running = true;
        while(!stop){
            try {
                Thread.sleep(5000);
                tcpClient.esvaziarBufferTopicos();
                tcpClient.esvaziarBufferIdeias();
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
}
