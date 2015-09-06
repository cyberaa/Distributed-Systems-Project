/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import objectos.Ideia;
import objectos.User;

/**
 *
 * @author Iris
 */
public class CreateIdeia implements Serializable{
    private User user;
    private Ideia ideia;
    private String nomeTopico;
    private double valorInvestido;
    public CreateIdeia(User user, Ideia ideia, String nomeTopico, double valorInvestido) {
        this.user = user;
        this.ideia = ideia;
        this.nomeTopico = nomeTopico;
        this.valorInvestido = valorInvestido;
    }

   

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ideia getIdeia() {
        return ideia;
    }

    public void setIdeia(Ideia ideia) {
        this.ideia = ideia;
    }

    public String getNomeTopico() {
        return nomeTopico;
    }

    public void setNomeTopico(String nomeTopico) {
        this.nomeTopico = nomeTopico;
    }

    public double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }
    
    
    
    
}
