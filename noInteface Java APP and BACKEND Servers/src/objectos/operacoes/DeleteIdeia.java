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
public class DeleteIdeia implements Serializable{
    private User user;
    private int referenciaIdeia;
    
    private boolean sucesso;
    private String mensagem;

    public DeleteIdeia(User user, int referenciaIdeia) {
        this.user = user;
        this.referenciaIdeia = referenciaIdeia;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getReferenciaIdeia() {
        return referenciaIdeia;
    }

    public void setReferenciaIdeia(int referenciaIdeia) {
        this.referenciaIdeia = referenciaIdeia;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
