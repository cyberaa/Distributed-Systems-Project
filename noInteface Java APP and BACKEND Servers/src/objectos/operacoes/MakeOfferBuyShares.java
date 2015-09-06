
package objectos.operacoes;

import java.io.Serializable;
import objectos.User;

public class MakeOfferBuyShares implements Serializable{
    private User user;
    private int referenciaIdeia;
    private int numAccoes;
    private double valorPorAccao;
    private boolean sucesso;
    private String mensagem;

    public MakeOfferBuyShares() {
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

    public int getNumAccoes() {
        return numAccoes;
    }

    public void setNumAccoes(int numAccoes) {
        this.numAccoes = numAccoes;
    }

    public double getValorPorAccao() {
        return valorPorAccao;
    }

    public void setValorPorAccao(double valorPorAccao) {
        this.valorPorAccao = valorPorAccao;
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
