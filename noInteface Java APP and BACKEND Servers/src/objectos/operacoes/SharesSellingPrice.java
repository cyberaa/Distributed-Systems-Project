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
public class SharesSellingPrice implements Serializable{
    private User user;
    private int referenciaIdeia;
    private double novoValor;
    private boolean sucesso;
    private String mensagem;

    public SharesSellingPrice() {
    }

    public SharesSellingPrice(User user, int referenciaIdeia, double novoValor) {
        this.user = user;
        this.referenciaIdeia = referenciaIdeia;
        this.novoValor = novoValor;
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

    public double getNovoValor() {
        return novoValor;
    }

    public void setNovoValor(double novoValor) {
        this.novoValor = novoValor;
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
