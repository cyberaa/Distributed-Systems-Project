
package objectos;

import java.io.Serializable;

public class PropostaCompra implements Serializable{
    private int referenciaIdeia;
    private int idProprietario;
    private int idComprador;
    private int numAccoes;
    private double valorPorAccao;

    public PropostaCompra() {
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

   
    public int getReferenciaIdeia() {
        return referenciaIdeia;
    }

    public void setReferenciaIdeia(int referenciaIdeia) {
        this.referenciaIdeia = referenciaIdeia;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
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
    
}
