/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ValidationAware;
import java.util.Map;
import objectos.User;
import org.apache.struts2.interceptor.SessionAware;

public class LoginAction extends BaseAction implements SessionAware, ValidationAware {

    private User user;
    private Client client;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        client = (Client) session.get("client");
        if (client != null) {
            if (client.connect()) {
                if (client.isRmiIsUp()) {
                    client.setUser(user);
                    if (!client.isLogged()) {
                        if (client.doLogin()) {
                            client.setLogged(true);
                            setName(client.getUser().getNome());
                            setNumNoticacao(5);
                            session.put("user", client.getUser());
                            session.put("client", client);
                            
                        } else {
                            addActionError("Username/Password inválido. Por favor, tenta outra vez.");
                            return "error";
                        }
                    }
                }else{
                    addActionError("Servidor está em baixo");
                }
            }else{
                addActionError("Servidor está em baixo");
            }
        } else {
            client = new Client();
            if (client.connect()) {
                if (client.isRmiIsUp()) {
                    if (!client.isLogged()) {
                        client.setUser(user);
                        if (client.doLogin()) {
                            client.setLogged(true);
                            setName(client.getUser().getNome());
                            setNumNoticacao(5);
                            session.put("user", client.getUser());
                            session.put("client", client);
                            
                        } else {
                            addActionError("Username/Password inválido. Por favor, tenta outra vez.");
                            return "error";
                        }
                    }
                }else{
                    addActionError("Servidor está em baixo");
                    return "error";
                }
            }else{
                addActionError("Servidor está em baixo");
                return "error";
            }
         }
        return SUCCESS;
    }

    @Override
    public void validate() {
//        String username = user.getUsername();
//        String pass = user.getPassw();
    }
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user
    ) {
        this.user = user;
    }

    @Override
    public void setSession(Map<String, Object> session
    ) {
        this.session = session;
    }

}
