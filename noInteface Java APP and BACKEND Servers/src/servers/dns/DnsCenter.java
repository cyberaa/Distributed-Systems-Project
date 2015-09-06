/********************************************************************************************************************************
 * Esta classe serve para controlar os servidores. Os servidores pedem o registo dos seus IP's e os respectivos PORTOS,
 * e essa classe por sua vez, guarda-os numa tabela. Essa tabela tem a informação tando do servidor RMI como do
 * servidor TCP. Neste momento só suporta dois servidores em simultâneo(Master/Slave). Os servidores enviam de um em
 * um segundo uma mensagem UDP a informar o ControlServer de que estão ALIVE. Este por sua vez reponde, tambm por UDP,
 * a dizer que tudo esta OK. Caso o ControlServer fique sem nenhuma informação de um servidor após um periodo de 10 segundos,
 * ele considera que o servidor foi a baixo e actualiza a sua tabela de forma a que aquele que estiver UP seja o MASTER.
 * Quando o servidor que estava DOWN passar a estar UP então é actulizado a tabela e esse último fica como SLAVE.
 ********************************************************************************************************************************/

package servers.dns;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Iris
 */
public class DnsCenter{
    //private static String[][] tableServer = new String[4][2];
    private static ArrayList<Server> tableServer = new ArrayList<Server>();
    private int portoTCP;
    private int portoUDP;
    public DnsCenter(int portoUDP, int portoTCP) {
        this.portoUDP = portoUDP;
        this.portoTCP = portoTCP;

    }

    public void init() {
        try {
            // Esta thread é responsável para enviar por UDP o estado do servidor (I_AM_ALIVE)
            ClientServices udp = new ClientServices(portoUDP);
            udp.start();

            //int serverPort = 5000;
            Socket serverSocket;
            ServerSocket listenSocket;
            System.out.println("Server Monitor Port: " + portoTCP);
            listenSocket = new ServerSocket(portoTCP);


            System.out.println("[Control Center] Servers Online :" + tableServer.size());
            while (true) {
                serverSocket = listenSocket.accept();
                System.out.println("[Control Center] Receive new Connection from: " + serverSocket.getRemoteSocketAddress());
                try {
                    ServerServices serverMonitor = new ServerServices(serverSocket);
                    serverMonitor.start();
                } catch (Exception ex) {
                    Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    //System.out.println("[Control Center] Users Online :" + tableServer.size());
                }

            }
        } catch (IOException e) {

            System.out.println("Listen:" + e.getMessage());
        }
    }

    public ArrayList<Server> getTableServer() {
        return tableServer;
    }

    public void setTableServer(ArrayList<Server> tableServe) {
        tableServer = tableServe;
    }

    public static synchronized String getMasterServer() {
        String masterServer = null;
        for (int i = 0; i < tableServer.size(); i++) {
            Server serv = tableServer.get(i);
            if (serv.getType().equalsIgnoreCase("Master")) {
                masterServer = serv.getIpaddressTCP() + " " + serv.getIpaddressRMI();
            }
        }
        return masterServer;
    }
    public static synchronized ObjectOutputStream getMasterServerOutputStream() {
        for (int i = 0; i < tableServer.size(); i++) {
            Server serv = tableServer.get(i);
            if (serv.getType().equalsIgnoreCase("Master")) {
                return serv.getOos();
            }
        }
        return null;
    }
    public static synchronized boolean recordServer(String msg, ObjectInputStream ois, ObjectOutputStream oos) {
        String dados[] = msg.split(":");
        String dadosTCP = dados[1] + ":" + dados[2];
        String dadosRMI = dados[1] + ":" + dados[3];
        boolean result = false;
        if (dados[0].equals("RECORD_SERVER")) {
            if (tableServer.isEmpty()) {
                Server server = new Server("Master", dadosTCP, dadosRMI, ois, oos);
                tableServer.add(server);
                result = true;

            } else if (tableServer.size() == 1) {
                Server server = tableServer.get(0);
                if (server.getType().equalsIgnoreCase("Master")) {
                    Server s = new Server("Slave", dadosTCP, dadosRMI, ois, oos);
                    if (!existServer(s)) {
                        tableServer.add(s);
                        result = true;
                    }
                }
            }
            System.out.println("[Control Center] Servers Online :" + tableServer.size());

            printTable();
        }
        return result;
    }

    public static synchronized void updateTable(ObjectInputStream ois) {
        int pos = -1;
        for (int i = 0; i < tableServer.size(); i++) {
            if (tableServer.get(i).getOis().equals(ois)) {
                pos = i;
                break;
            }
        }
        if (pos != -1) {
            tableServer.remove(pos);
        }

        if (tableServer.size() == 1) {
            if (tableServer.get(0).getType().equalsIgnoreCase("Slave")) {
                tableServer.get(0).setType("Master");
            }
        }
        System.out.println("[Control Center] Servers Online :" + tableServer.size());
        printTable();

    }

    public static boolean existServer(Server server) {
        for (int i = 0; i < tableServer.size(); i++) {
            Server serv = tableServer.get(i);
            if (serv.getIpaddressRMI().equals(server.getIpaddressRMI()) && serv.getIpaddressTCP().equals(server.getIpaddressTCP())) {
                return true;
            } else if (serv.getIpaddressRMI().equals(server.getIpaddressRMI()) || serv.getIpaddressTCP().equals(server.getIpaddressTCP())) {
                return true;
            }
        }
        return false;
    }

    private static void printTable() {
        for (int i = 0; i < tableServer.size(); i++) {
            Server server = tableServer.get(i);
            System.out.println("******SERVER " + (i + 1) + "**********");
            System.out.println(server.toString());
        }
        System.out.println("****************************");
    }

    public static void main(String args[]) {
        DnsCenter c = new DnsCenter(6789, 6790);
        try {
            c.init();
        } catch (Exception ex) {
            Logger.getLogger(DnsCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
