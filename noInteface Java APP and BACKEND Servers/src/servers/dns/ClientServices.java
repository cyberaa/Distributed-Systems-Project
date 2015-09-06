/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servers.dns;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iris
 */
public class ClientServices extends Thread{
    private int porto;
    private boolean flag = false;
    public ClientServices(int porto){
        this.porto = porto;
    }
    @Override
    public void run(){
        connect();
    }
    private void connect(){
        DatagramSocket aSocket = null;
        String s;
        try {
            aSocket = new DatagramSocket(porto);
        } catch (SocketException ex) {
            Logger.getLogger(ClientServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Client monitor listening on port " + porto);
        
        while (true) {

            try {
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.setSoTimeout(1000);
                aSocket.receive(request);

                flag = true;
                
                s = new String(request.getData());

                System.out.println("Server Recebeu: " + s.trim());

                if (flag) {
                    DatagramPacket reply = handleMessage(request);
                    aSocket.send(reply);
                    flag=false;
                }
                

            } catch (SocketException e) {
                //System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                //System.out.println("IO: " + e.getMessage());
                //flag = false;
            }
        }
    }
    public DatagramPacket handleMessage(DatagramPacket packet) {
        String s = new String(packet.getData());
        String dados[] = s.trim().split(":");
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        //System.out.println(dados[0] + ":" + dados[1] + ":" + dados[2]);
        if (dados[0].equals("DISCOVER_MASTER_SERVER")) {
            String masterServer = DnsCenter.getMasterServer();
            if(masterServer==null){
                masterServer = "";
            }
            packet = new DatagramPacket(masterServer.getBytes(), masterServer.getBytes().length, address, port);
        
        } 
        return packet;

    }
}
