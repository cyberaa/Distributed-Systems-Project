/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.util.Date;
import objectos.Transaccao;

/**
 *
 * @author Iris
 */
public class TransaccaoBean {

    private int idTransaccao = 0;
    private int idVendedor = 0;
    private int idComprador = 0;
    private int idIdeia = 0;
    private String numAccoes ="";
    private String valorTotal="";
    private String dataTransaccao="";
    private String tipoCompra="";

    public TransaccaoBean() {
    }

    public TransaccaoBean(Transaccao t) {
        this.numAccoes = Integer.toString(t.getNumAccoes());
        this.valorTotal = Double.toString(t.getValorTotal());
        this.tipoCompra = t.getTipoCompra();
        this.dataTransaccao = formatDate(t.getDataTransaccao());
        this.idComprador = t.getIdComprador();
        this.idIdeia = t.getIdIdeia();
        this.idVendedor = t.getIdVendedor();
        this.idTransaccao = t.getIdTransaccao();

    }

    public Transaccao toTransaccao() {
        Transaccao transaccao = new Transaccao();
        transaccao.setNumAccoes(Integer.parseInt(this.numAccoes));
        transaccao.setIdComprador(this.idComprador);
        transaccao.setIdIdeia(this.idIdeia);
        transaccao.setIdTransaccao(this.idTransaccao);
        transaccao.setIdVendedor(this.idVendedor);
        transaccao.setTipoCompra(this.tipoCompra);
        transaccao.setValorTotal(Double.parseDouble(this.valorTotal));

        return transaccao;

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

    public String getNumAccoes() {
        return numAccoes;
    }

    public void setNumAccoes(String numAccoes) {
        this.numAccoes = numAccoes;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataTransaccao() {
        return dataTransaccao;
    }

    public void setDataTransaccao(String dataTransaccao) {
        this.dataTransaccao = dataTransaccao;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    private String formatDate(Timestamp timestamp) {
        Date d = new Date();
        d.setTime(timestamp.getTime());
        String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d);
        return date;
    }

}
