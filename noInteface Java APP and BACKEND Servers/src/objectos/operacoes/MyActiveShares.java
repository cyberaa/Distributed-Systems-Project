/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import objectos.Accao;
import objectos.User;

/**
 *
 * @author Iris
 */
public class MyActiveShares implements Serializable{
    private User user;
    private boolean sucesso;
    private String mensagem;
    private List<Accao> listaAccoes;
    public MyActiveShares() {
        this.listaAccoes = new ArrayList<>();
    }

    public MyActiveShares(User user) {
        this.listaAccoes = new ArrayList<>();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Accao> getListaAccoes() {
        return listaAccoes;
    }

    public void setListaAccoes(List<Accao> listaAccoes) {
        this.listaAccoes = listaAccoes;
    }
    
    
}
