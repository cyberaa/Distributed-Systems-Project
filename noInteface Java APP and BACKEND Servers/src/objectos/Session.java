/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos;

import clients.rmi.ClientInterface;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Session implements Serializable {

    private static Session _instance;
    //private HashMap<User, List<Object>> usersOnline;
    public HashMap<User, ClientInterface> usersOnline;

    public static Session getInstance() {
        if (_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }

    public Session() {
        usersOnline = new HashMap<>();
    }

//    public synchronized boolean addUserOnTableUsersOnline(User user, Object outUser) {
//        User u = existeUserOnTableUsersOnline(user);
//        if (u == null) {
//            List<Object> listaLigacoes = new ArrayList<>();
//            listaLigacoes.add(outUser);
//            usersOnline.put(user, listaLigacoes);
//            return true;
//        } else {
//            return usersOnline.get(u).add(outUser);
//        }
//    }
//    public synchronized boolean addUserOnTableUsersOnline(String username, User user) {
//        User u = existeUserOnTableUsersOnline(user);
//        if (u == null) {
//            usersOnline.put(username, user);
//            return true;
//        }
//        return false;
//    }
    public synchronized boolean addUserOnTableUsersOnline(User user, ClientInterface clientInterface) {
        User u = existeUserOnTableUsersOnline(user);
        if (u == null) {
            usersOnline.put(user, clientInterface);
            System.out.println("[RMI] Numero Users Online = " + usersOnline.size());
            return true;
        }
        return false;
    }
    public synchronized boolean updateClientInterfaceOnTableUsersOnline(User user, ClientInterface clientInterface) {
        User u = existeUserOnTableUsersOnline(user);
        if (u != null) {
            usersOnline.put(u, clientInterface);
            System.out.println("[RMI] Numero Users Online = " + usersOnline.size());
            return true;
        }
        return false;
    }

//    private boolean removeUser(User user) {
//        Iterator it = usersOnline.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pairs = (Map.Entry) it.next();
//            User u = (User) pairs.getKey();
//            if (u.getUsername().equals(user.getUsername())) {
//                it.remove();
//                return true;
//            }
//        }
//        return false;
//    }
    private boolean removeUser(User user) {
        Iterator it = usersOnline.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            User usr = (User) pairs.getKey();
            if (usr.getUsername().equals(user.getUsername())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

//    public synchronized boolean removeUserOnTableUsersOnline(User user) {
//        if (usersOnline != null && !usersOnline.isEmpty()) {
//            user = existeUserOnTableUsersOnline(user);
//            if (user != null) {
//                removeUser(user);
//                return true;
//            }
//        }
//        return false;
//    }
    public synchronized boolean removeUserOnTableUsersOnline(User user) {
        if (usersOnline != null && !usersOnline.isEmpty()) {
            User usr = existeUserOnTableUsersOnline(user);
            if (usr != null) {
                if (removeUser(usr)) {
                    System.out.println("[RMI]Numero de users Online = " + usersOnline.size());
                    return true;
                }

            }
        }
        return false;
    }

//    public User existeUserOnTableUsersOnline(User user) {
//        for (Map.Entry pairs : usersOnline.entrySet()) {
//            User u = (User) pairs.getKey();
//            if (u.getUsername().equals(user.getUsername())) {
//                user.cloneUser(u);
//                return u;
//            }
//        }
//        return null;
//    }
    public User existeUserOnTableUsersOnline(User user) {
        for (Map.Entry pairs : usersOnline.entrySet()) {
            User usr = (User) pairs.getKey();
            if (usr.getUsername().equals(user.getUsername())) {
                return usr;
            }
        }
        return null;
    }
    
    public User existeUserOnTableUsersOnline(String username) {
        for (Map.Entry pairs : usersOnline.entrySet()) {
            User usr = (User) pairs.getKey();
            if (usr.getUsername().equals(username)) {
                return usr;
            }
        }
        return null;
    }

//    public List<Object> getAllLigacoesUserOnTableUsersOnline(User user) {
//        for (Map.Entry pairs : usersOnline.entrySet()) {
//            User u = (User) pairs.getKey();
//            if (u.getUsername().equals(user.getUsername())) {
//                return (List<Object>) pairs.getValue();
//            }
//        }
//        return null;
//    }
//    public ObjectOutputStream getObjectOutputStreamUserOnTableUsersOnline(User user) {
//        for (Map.Entry pairs : usersOnline.entrySet()) {
//            User u = (User) pairs.getKey();
//            if (u.getUsername().equals(user.getUsername())) {
//                Object obj = pairs.getValue();
//                if (obj instanceof ObjectOutputStream) {
//                    return (ObjectOutputStream) obj;
//                }
//            }
//        }
//        return null;
//    }
//    public ObjectOutputStream getObjectOutputStreamUserOnTableUsersOnline(User user) {
//        for (Map.Entry pairs : usersOnline.entrySet()) {
//            User u = (User) pairs.getKey();
//            if (u.getUsername().equals(user.getUsername())) {
//                List<Object> listaLigacoes = (List<Object>) pairs.getValue();
//                for (Object object : listaLigacoes) {
//                    if (object instanceof ObjectOutputStream) {
//                        return (ObjectOutputStream) object;
//                    }
//                }
//            }
//        }
//        return null;
//    }
    public ClientInterface getClientInterfaceUserOnTableUsersOnline(User user) {
        for (Map.Entry pairs : usersOnline.entrySet()) {
            User u = (User) pairs.getKey();
            if (u.getUsername().equals(user.getUsername())) {
                ClientInterface clientInterface = (ClientInterface) pairs.getValue();
                if (clientInterface!=null) {
                    return clientInterface;
                }
            }
        }
        return null;
    }
//    public synchronized HashMap<User, List<Object>> getUsersOnline() {
//        return usersOnline;
//    }

    public synchronized HashMap<User, ClientInterface> getUsersOnline() {
        return usersOnline;
    }

}
