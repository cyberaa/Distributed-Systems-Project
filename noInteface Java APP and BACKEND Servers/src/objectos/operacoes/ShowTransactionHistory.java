/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import objectos.Transaccao;
import objectos.User;

/**
 *
 * @author Iris
 */
public class ShowTransactionHistory implements Serializable{
    private User user;
    private List<Transaccao> listaTransaccoes;
    private boolean sucesso;
    private String mensagem;
    public ShowTransactionHistory() {
        this.listaTransaccoes = new ArrayList<>();
    }

    public ShowTransactionHistory(User user) {
        this.user = user;
        this.listaTransaccoes = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaccao> getListaTransaccoes() {
        return listaTransaccoes;
    }

    public void setListaTransaccoes(List<Transaccao> listaTransaccoes) {
        this.listaTransaccoes = listaTransaccoes;
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
