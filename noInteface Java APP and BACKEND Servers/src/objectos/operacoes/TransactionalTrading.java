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
public class TransactionalTrading implements Serializable{
    private User user;
    private int referenciaDaIdeia;
    private int numeroAccoes;
    private boolean sucesso;
    private String mensagem; 
    
    public TransactionalTrading() {
    }

    public TransactionalTrading(User user, int referenciaDaIdeia, int numeroAccoes) {
        this.user = user;
        this.referenciaDaIdeia = referenciaDaIdeia;
        this.numeroAccoes = numeroAccoes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumeroAccoes() {
        return numeroAccoes;
    }

    public void setNumeroAccoes(int numeroAccoes) {
        this.numeroAccoes = numeroAccoes;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public int getReferenciaDaIdeia() {
        return referenciaDaIdeia;
    }

    public void setReferenciaDaIdeia(int referenciaDaIdeia) {
        this.referenciaDaIdeia = referenciaDaIdeia;
    }
}
