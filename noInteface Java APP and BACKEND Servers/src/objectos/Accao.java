/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos;

import java.io.Serializable;

/**
 *
 * @author Iris
 */
public class Accao implements Serializable{
    private int idAccao;
    private int idUtilizador;
    private int idIdeia;
    private int numAccoes;
    private double valorTotal;
    private String estado;
    private String tipoCompra;

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }
    public Accao() {
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public int getIdIdeia() {
        return idIdeia;
    }

    public void setIdIdeia(int idIdeia) {
        this.idIdeia = idIdeia;
    }

    public int getNumAccoes() {
        return numAccoes;
    }

    public void setNumAccoes(int numAccoes) {
        this.numAccoes = numAccoes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdAccao() {
        return idAccao;
    }

    public void setIdAccao(int idAccao) {
        this.idAccao = idAccao;
    }

    @Override
    public String toString() {
        return "Accao{" + "idAccao=" + idAccao + ", idUtilizador=" + idUtilizador + ", idIdeia=" + idIdeia + ", numAccoes=" + numAccoes + ", valorTotal=" + valorTotal + ", estado=" + estado + ", tipoCompra=" + tipoCompra + '}';
    }

    

   
    
    
}
