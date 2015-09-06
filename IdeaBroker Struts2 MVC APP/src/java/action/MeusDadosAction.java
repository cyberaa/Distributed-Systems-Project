/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import objectos.User;

/**
 *
 * @author Iris
 */
public class MeusDadosAction extends BaseAction{
    private User user;
    @Override
    public String execute() throws Exception {
        setPath("/meusDados");
        Map session = ActionContext.getContext().getSession();
        user = (User)session.get("user");
        return SUCCESS;

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
    
}
