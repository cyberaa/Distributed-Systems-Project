package servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servers.tcp.TCPSERVER;

public class SlaveServer {

    public static Socket s;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public static void main(String[] args) throws Exception {
        int serverPort = 6790;
        s = new Socket("localhost", serverPort);
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
        
        if (recordServer(5020,4000)) {
            System.out.println("Servidor registado");
//            new ServerRMI(session,4000);
//            new ServerSocket(session,5020, 5000, 4000);
            new TCPSERVER(5020, 5000);
        } else {
            System.out.println("ERROR: Não foi possivel resgistar o servidor!\nCAUSE: Esse servidor já está a correr!\nSOLUTION: Verifique se nenhum porto coincide com os dos servidores que já estão a correr...");
        }
    }

    private static boolean recordServer(int portoTCP,int portoRMI) {
        try {
            InetAddress aHost = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MasterServer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if (obj instanceof String) {
                    String res = (String) obj;
                    if (res.equalsIgnoreCase("Server registered")) {
                        stop = true;
                        value = true;
                    }
                }
            }


        } catch (UnknownHostException ex) {
            Logger.getLogger(SlaveServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SlaveServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketTimeoutException ex) {
            System.out.println("Socket exception: ultrapassou o tempo de espera");
        } catch (IOException ex) {
            Logger.getLogger(SlaveServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
}
