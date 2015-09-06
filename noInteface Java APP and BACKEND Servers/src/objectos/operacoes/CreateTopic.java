/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import objectos.Tema;
import objectos.User;

/**
 *
 * @author Iris
 */
public class CreateTopic implements Serializable{
    private Tema tema;
    private User user;

    public CreateTopic(Tema tema, User user) {
        this.tema = tema;
        this.user = user;
    }
    
    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
