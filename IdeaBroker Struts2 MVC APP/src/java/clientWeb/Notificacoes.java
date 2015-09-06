/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientWeb;

import clients.rmi.ClientInterface;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import servers.rmi.RMI_Interface;

/**
 *
 * @author Iris
 */
public class Notificacoes extends WebSocketServlet {

    private final Set<ChatMessageInbound> connections = new CopyOnWriteArraySet<ChatMessageInbound>();
    private Registry registry;
    private RMI_Interface serverInterface;
    public Client user;
    
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        user = (Client) session.getAttribute("user");
        return new ChatMessageInbound(user);
    }

    public void init() throws ServletException {
        try {
            serverInterface = (RMI_Interface) Naming.lookup("rmi://localhost:7500/dbs");
//            registry = LocateRegistry.getRegistry(7500);
//            serverInterface = (RMI_Interface) registry.lookup("dbs");
        } catch (AccessException e) {
            throw new ServletException(e);
        } catch (RemoteException ex) {
            Logger.getLogger(Notificacoes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Notificacoes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Notificacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private final class ChatMessageInbound extends MessageInbound {

        private final String nickname;

        private ChatMessageInbound(Client user) {

            this.nickname = user.getUser().getUsername();
        }

        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
            try{
                serverInterface.operationJoin(user.getUser(),(ClientInterface)user);
            }catch(RemoteException ex){
                
            }

        }

        protected void onClose(int status) {
            connections.remove(this);
            try{
                serverInterface.removeUserTableUsersOnline(user.getUser());
            }catch(RemoteException ex){
                
            }
        }

        protected void onTextMessage(CharBuffer message) throws IOException {
            // never trust the client
            String filteredMessage = filter(message.toString());
            broadcast("&lt;" + nickname + "&gt; " + filteredMessage);
        }

        private void broadcast(String message) { // send message to all
            for (ChatMessageInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                }
            }
        }

        public String filter(String message) {
            if (message == null) {
                return (null);
            }
            // filter characters that are sensitive in HTML
            char content[] = new char[message.length()];
            message.getChars(0, message.length(), content, 0);
            StringBuilder result = new StringBuilder(content.length + 50);
            for (int i = 0; i < content.length; i++) {
                switch (content[i]) {
                    case '<':
                        result.append("&lt;");
                        break;
                    case '>':
                        result.append("&gt;");
                        break;
                    case '&':
                        result.append("&amp;");
                        break;
                    case '"':
                        result.append("&quot;");
                        break;
                    default:
                        result.append(content[i]);
                }
            }
            return (result.toString());
        }

        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException("Binary messages not supported.");
        }
    }

}
