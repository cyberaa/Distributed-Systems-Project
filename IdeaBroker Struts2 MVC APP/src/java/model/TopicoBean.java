/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Timestamp;
import java.util.Date;
import objectos.Tema;

/**
 *
 * @author Iris
 */
public class TopicoBean {
    private int idTopico = 0;
    private int idUtilizador = 0;
    private String username="";
    private String titulo="";
    private String hashtag = "";
    private String dataCriacao="";
    
    public TopicoBean(){
        
    }
    public TopicoBean(Tema tema){
        this.idTopico = tema.getIdTema();
        this.idUtilizador = tema.getIdUtilizador();
        this.hashtag = tema.getHashtag();
        this.titulo = tema.getNome();
        this.dataCriacao = formatDate(tema.getDataCriacao());
        this.username = tema.getUsername();
    }
    public Tema toTopico(){
        Tema tema = new Tema();
        tema.setIdTema(idTopico);
        tema.setIdUtilizador(idUtilizador);
        tema.setHashtag(hashtag);
        tema.setNome(titulo);
        tema.setUsername(username);
        return tema;
    }
    private String formatDate(Timestamp timestamp){
        Date d = new Date();
        d.setTime(timestamp.getTime());
        String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d);
        return date;
    }

    public int getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(int idTopico) {
        this.idTopico = idTopico;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
}
