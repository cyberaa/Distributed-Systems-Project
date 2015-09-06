/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import objectos.User;

/**
 *
 * @author Iris
 */
public class Registo implements Serializable{
    private User user;
    public Registo(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
