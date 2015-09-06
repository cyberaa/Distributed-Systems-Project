package servers.tcp;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import objectos.PedidoCompra;
import objectos.Session;
import objectos.User;
import servers.rmi.RMI_Interface;

public class TCPSERVER{

    private static HashMap<User, ObjectOutputStream> usersOnline = new HashMap();
    public static RMI_Interface rMI_Interface;
    public static HashMap<String, List<Object>> bufferMensagens = new HashMap<>();
    private ThreadLimpaBuffer limpaBuffer;
    private Session session;
    public TCPSERVER(int porto1, int porto2)throws NotBoundException, MalformedURLException, RemoteException {
        init(porto1,porto2);
        //Load mensagens de buffer guardadas na BD
    }
    private void init(int porto1, int porto2) throws NotBoundException, MalformedURLException, RemoteException{
        initRMIInterface();
        initTheadLimpaBuffer();
        limpaBuffer.start();
        int clientes_ligados = 0;
        try {
            int serverPort = porto1;
            System.out.println("A inicializar Servidor TCP ...");
            System.out.println("Start Listening on Port "+serverPort+" ...");
            ServerSocket listensocket = new ServerSocket(serverPort);
            System.out.println("Listening on Socket = " + listensocket);

            System.out.println("-------------------------");

            while (true) {
                Socket clientsocket = listensocket.accept();

                System.out.println("Client_Socket(created at accept()) = " + clientsocket);
                clientes_ligados++;
                final RMI_Interface rmi = rMI_Interface;
                new TratamentoLigacoes(clientsocket, clientes_ligados, rmi);
            }
        } catch (IOException e) {
            System.out.println("Listen: " + e.getMessage());
        }
    }
    public void initTheadLimpaBuffer(){
        this.limpaBuffer = new ThreadLimpaBuffer(this);
        this.limpaBuffer.setDaemon(true);    
    }
//    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
//        TCPSERVER s = new TCPSERVER();
//        initRMIInterface();
//        // inicia a thread para limpar o buffer;
//        initTheadLimpaBuffer();
//        limpaBuffer.start();
//        int clientes_ligados = 0;
//        try {
//            int serverPort = 6000;
//            System.out.println("A inicializar Servidor TCP ...");
//            System.out.println("Start Listening on Port 6000 ...");
//            ServerSocket listensocket = new ServerSocket(serverPort);
//            System.out.println("Listening on Socket = " + listensocket);
//
//            System.out.println("-------------------------");
//
//            while (true) {
//                Socket clientsocket = listensocket.accept();
//
//                System.out.println("Client_Socket(created at accept()) = " + clientsocket);
//                clientes_ligados++;
//                final RMI_Interface rmi = rMI_Interface;
//                new TratamentoLigacoes(clientsocket, clientes_ligados, rmi);
//            }
//        } catch (IOException e) {
//            System.out.println("Listen: " + e.getMessage());
//        }
//        
//    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    public void initRMIInterface() throws NotBoundException, MalformedURLException, RemoteException {
        rMI_Interface = (RMI_Interface) Naming.lookup("rmi://localhost:7500/dbs");
        String a = rMI_Interface.sayHello();
        System.out.println(a);
        System.out.println("Server Reached...");
        
    }
    public static synchronized boolean addUserOnTableUsersOnline(User user, ObjectOutputStream outUser) {
        if (usersOnline == null) {
            usersOnline = new HashMap();
        }
        if (existeUserOnTableUsersOnline(user) == null) {
            usersOnline.put(user, outUser);
            System.out.println("[TCPSERVER] - Numero Users Online = "+usersOnline.size());
            return true;
        }
        return false;
    }
    private static boolean removeUser(User user){
        Iterator it = usersOnline.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            User u = (User) pairs.getKey();
            if (u.getUsername().equals(user.getUsername())) {
                it.remove();
                return true;
            }
        }
        return false;
    }
    public static synchronized boolean removeUserOnTableUsersOnline(User user) {
        if (usersOnline != null && !usersOnline.isEmpty()) {
            user = existeUserOnTableUsersOnline(user);
            if (user != null) {
                //usersOnline.remove(user);
                if(removeUser(user)){
                    System.out.println("[TCPSERVER] - Numero Users Online = "+usersOnline.size());
                    return true;
                }
            }
        }
        return false;
    }

    public static User existeUserOnTableUsersOnline(User user) {
        for (Map.Entry pairs : usersOnline.entrySet()) {
            User u = (User) pairs.getKey();
            if (u.getUsername().equals(user.getUsername())) {
                user.cloneUser(u);
                return u;
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    public static User existeUserOnTableUsersOnline(String username) {
        for (Map.Entry pairs : usersOnline.entrySet()) {
            User user = (User) pairs.getKey();
            if (user.getUsername().equals(username)) {
                return user;
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    public static ObjectOutputStream getLigacaoUserOnTableUsersOnline(User user) {
        for (Map.Entry pairs : usersOnline.entrySet()) {
            User u = (User) pairs.getKey();
            if (u.getUsername().equals(user.getUsername())) {
                return (ObjectOutputStream) pairs.getValue();
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }
    public synchronized static void adicionaMensagemNoBuffer(String username, Object msg) {
        if(bufferMensagens.containsKey(username)){
            bufferMensagens.get(username).add(msg);
        }else{
            List<Object> lista = new ArrayList<>();
            lista.add(msg);
            bufferMensagens.put(username, lista);
        }
    }

    public synchronized void esvaziarBuffer() {
        List<String> listaKeysParaRemoverDoBuffer = new ArrayList<>();

        if (!bufferMensagens.isEmpty()) {
            for (Map.Entry pairs : bufferMensagens.entrySet()) {
                String username = (String) pairs.getKey();
                List<Object> listaMsg = (List<Object>) pairs.getValue();
                User user = existeUserOnTableUsersOnline(username);
                if (user != null) {
                    ObjectOutputStream out = getLigacaoUserOnTableUsersOnline(user);
                    if(out!=null){
                        int countIndex=0;
                        for (int index=0; index<listaMsg.size(); index++) {
                            Object msg = listaMsg.get(index);
                            if(sendMensagem(msg, out)){
                                countIndex++;
                            }
                        }
                        if (countIndex == listaMsg.size()) {
                            listaKeysParaRemoverDoBuffer.add(username);
                        }
                    }
                }
            }
        }
        if (!listaKeysParaRemoverDoBuffer.isEmpty()) {
            for (String string : listaKeysParaRemoverDoBuffer) {
                bufferMensagens.remove(string);
            }
        }
    }

    

    private static boolean sendMensagem(Object obj, ObjectOutputStream out) {
        try {
            out.writeObject(obj);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
