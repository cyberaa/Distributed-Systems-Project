/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Timestamp;
import java.util.Date;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public class IdeiaBean {
    private int idIdeia=0;
    private int idTopico=0;
    private int idUtilizador=0;
    private String tituloTopico="";
    private String titulo="";
    private String descricao="";
    private String montante="";
    private String percentagem="";
    private String dataCriacao="";
    private String username="";
    public IdeiaBean() {
        
    }
    
    public IdeiaBean(String tituloTopico) {
        this.tituloTopico = tituloTopico;
    }
    
    public IdeiaBean(Ideia ideia) {
        this.tituloTopico = ideia.getNomeTopico();
        this.titulo = ideia.getNome();
        this.percentagem = Integer.toString(ideia.getVenda_automatica());
        this.idIdeia = ideia.getId_ideia();
        this.idUtilizador = ideia.getId_utilizador();
        this.idTopico = ideia.getId_topico();
        if(ideia.getUsername()!=null){
            this.username = ideia.getUsername();
        }
        if(ideia.getDescricao()!=null){
            this.descricao = ideia.getDescricao();
        }
        
        this.dataCriacao = formatDate(ideia.getDataCriacao());
        
    }
    public Ideia toIdeia() {
        Ideia ideia = new Ideia();
        ideia.setNomeTopico(this.tituloTopico);
        ideia.setNome(this.titulo);
        ideia.setVenda_automatica(Integer.parseInt(this.percentagem));
        ideia.setId_ideia(this.idIdeia);
        ideia.setId_utilizador(this.idUtilizador);
        ideia.setId_topico(this.idTopico);
        ideia.setUsername(this.username);
        ideia.setDescricao(this.descricao);
        return ideia;
        
    }
    public int getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(int idTopico) {
        this.idTopico = idTopico;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getIdIdeia() {
        return idIdeia;
    }

    public void setIdIdeia(int idIdeia) {
        this.idIdeia = idIdeia;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    
    public String getTituloTopico() {
        return tituloTopico;
    }

    public void setTituloTopico(String tituloTopico) {
        this.tituloTopico = tituloTopico;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMontante() {
        return montante;
    }

    public void setMontante(String montante) {
        this.montante = montante;
    }

    public String getPercentagem() {
        return percentagem;
    }

    public void setPercentagem(String percentagem) {
        this.percentagem = percentagem;
    }
    
    private String formatDate(Timestamp timestamp){
        Date d = new Date();
        d.setTime(timestamp.getTime());
        String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d);
        return date;
    }
    
}
