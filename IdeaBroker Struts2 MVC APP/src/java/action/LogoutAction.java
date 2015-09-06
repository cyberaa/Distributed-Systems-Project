/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import clientWeb.Client;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

/**
 *
 * @author Iris
 */
public class LogoutAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                client.doLogout();
            }
            session.remove("client");
            session.remove("user");
        }
        return SUCCESS;

    }

}
