/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import objectos.Tema;
import objectos.User;

/**
 *
 * @author Iris
 */
public class AllMyTopic implements Serializable{
    private User user;
    private List<Tema> listaTemas;
    public AllMyTopic(User user) {
        this.user = user;
        this.listaTemas = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tema> getListaTemas() {
        return listaTemas;
    }

    public void setListaTemas(List<Tema> listaTemas) {
        this.listaTemas = listaTemas;
    }
    
}
