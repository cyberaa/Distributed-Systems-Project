/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import clientWeb.Client;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.util.Map;
import objectos.User;

/**
 *
 * @author hevora
 */
public abstract class BaseAction extends ActionSupport implements Preparable{
    private String path;
    private String name;
    private User user;
    private String zona;
    private Client client;
    private int numNoticacao;
    @Override
    public void prepare() throws Exception {
        // set session-level template variables
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.containsKey("user")) {
            User userBean = (User) session.get("user");
            user = userBean;
            name = userBean.getNome();
        }
        if (session.containsKey("client")) {
            client = (Client) session.get("client");
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getNumNoticacao() {
        return numNoticacao;
    }

    public void setNumNoticacao(int numNoticacao) {
        this.numNoticacao = numNoticacao;
    }
    
}
