package servers.rmi;


import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import objectos.PedidoCompra;
import objectos.Session;
import objectos.User;
import objectos.operacoes.Notification;

public class RMI_Server {

    public RMI_Server() {
        
    }
    static HashMap<String, List<Notification>> notificacoes = new HashMap();
    static HashMap<Integer, PedidoCompra> pedidosCompras = new HashMap<>();
    static ArrayList<String> online_users = new ArrayList();
    static User user;
    public static Session session;
    public static void main(String[] args) throws SQLException, IOException {
        // TODO Auto-generated method stub
        //save_everything();
        session = Session.getInstance();
        try {
            RMI_ServerImpl rMI_ServerImpl = new RMI_ServerImpl();
            rMI_ServerImpl.setSession(session);
            rMI_ServerImpl.initThreadRMIClientConnection();
            rMI_ServerImpl.initThreadLimpaBuffer();
            
            Registry h = LocateRegistry.createRegistry(7500);
            h.rebind("dbs", rMI_ServerImpl);
            System.out.println("Creating Registry ...");
            System.out.println("RMI Ready ...");
            
        } catch (RemoteException re) {
            System.out.println("Exception in Impl.main: " + re);
        }
    }
    
}