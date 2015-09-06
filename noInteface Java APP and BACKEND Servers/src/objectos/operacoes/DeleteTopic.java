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
public class DeleteTopic implements Serializable{
    private User user;
    private String nomeTopico;

    public DeleteTopic(User user, String nomeTopico) {
        this.user = user;
        this.nomeTopico = nomeTopico;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNomeTopico() {
        return nomeTopico;
    }

    public void setNomeTopico(String nomeTopico) {
        this.nomeTopico = nomeTopico;
    }
    
    
}
