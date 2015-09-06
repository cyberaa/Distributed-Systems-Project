package objectos;

import java.io.Serializable;
import java.sql.Timestamp;

public class Transaccao implements Serializable {

    private int idTransaccao;
    private int idVendedor;
    private int idComprador;
    private int idIdeia;
    private int numAccoes;
    private double valorTotal;
    private Timestamp dataTransaccao;
    private String tipoCompra;

    public Transaccao() {
    }

    public int getIdTransaccao() {
        return idTransaccao;
    }

    public void setIdTransaccao(int idTransaccao) {
        this.idTransaccao = idTransaccao;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
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

    public Timestamp getDataTransaccao() {
        return dataTransaccao;
    }

    public void setDataTransaccao(Timestamp dataTransaccao) {
        this.dataTransaccao = dataTransaccao;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    @Override
    public String toString() {
        return "Transaccao{" + "idTransaccao=" + idTransaccao + ", idVendedor=" + idVendedor + ", idComprador=" + idComprador + ", idIdeia=" + idIdeia + ", numAccoes=" + numAccoes + ", valorTotal=" + valorTotal + ", dataTransaccao=" + dataTransaccao + ", tipoCompra=" + tipoCompra + '}';
    }

    
    
}
