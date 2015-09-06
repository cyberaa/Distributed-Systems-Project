/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import objectos.Accao;

/**
 *
 * @author Iris
 */
public class AccaoBean {

    private int idAccao;
    private int idUtilizador;
    private int idIdeia;
    private String numAccoes;
    private String valorTotal;
    private String estado;
    private String tipoCompra;

    public AccaoBean(Accao a) {
        this.idAccao = a.getIdAccao();
        this.idUtilizador = a.getIdUtilizador();
        this.idIdeia = a.getIdIdeia();
        this.numAccoes = Integer.toString(a.getNumAccoes());
        this.valorTotal = Double.toString(a.getValorTotal());
        this.tipoCompra = a.getTipoCompra();
        this.estado = a.getEstado();

    }

    public Accao toTransaccao() {
        Accao accao = new Accao();
        accao.setNumAccoes(Integer.parseInt(this.numAccoes));

        accao.setIdUtilizador(this.idUtilizador);
        accao.setIdAccao(this.idAccao);
        accao.setIdIdeia(this.idIdeia);
        accao.setTipoCompra(this.tipoCompra);
        accao.setEstado(this.estado);
        accao.setValorTotal(Double.parseDouble(this.valorTotal));

        return accao;

    }

    public int getIdAccao() {
        return idAccao;
    }

    public void setIdAccao(int idAccao) {
        this.idAccao = idAccao;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

}
