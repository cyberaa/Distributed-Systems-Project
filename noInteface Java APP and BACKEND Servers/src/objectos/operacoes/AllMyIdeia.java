/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import objectos.Ideia;
import objectos.User;

/**
 *
 * @author Iris
 */
public class AllMyIdeia implements Serializable{
    private User user;
    private List<Ideia> listaIdeias;

    public AllMyIdeia(User user) {
        this.user = user;
        this.listaIdeias = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public List<Ideia> getListaIdeias() {
        return listaIdeias;
    }

    public void setListaIdeias(List<Ideia> listaIdeias) {
        this.listaIdeias = listaIdeias;
    }
    
    
    
    
}
