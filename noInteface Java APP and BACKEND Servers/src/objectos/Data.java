package objectos;


import java.io.Serializable;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class Data implements Serializable {
    
    private String tipo;
    private User user;
    private String mensagem;
    private Tema tema;
    private Ideia ideia;
    private String t;
    private Vector <Tema> temas = new Vector <Tema>();
    private Vector <Ideia> ideias = new Vector <Ideia>();
    private Vector <Transaccao> trans = new Vector <Transaccao>();
    private Vector <Log> logs = new Vector <Log>();
    private int preco,n_accoes;
    
    Data(String tipo,User user,Tema t){
        this.tipo = tipo;
        this.user = user;
        this.tema = t;
    }
    
    public Vector <Tema> getTemas(){
        return temas;
    }
 
    public void setLogs(Vector <Log> t){
        this.logs= t;
    }
    public Vector <Log> getLogs(){
        return logs;
    }
     public void setTrans(Vector <Transaccao> t){
        this.trans= t;
    }
    public Vector <Transaccao> getTrans(){
        return trans;
    }
    public void setTemas(Vector <Tema> t){
        this.temas = t;
    }
     public Vector <Ideia> getIdeas(){
        return ideias;
    }
    public void setIdeas(Vector <Ideia> t){
        this.ideias = t;
    }

    
    public Tema getTema() {
		return tema;
	}
    public void setTema(Tema tema) {
		this.tema = tema;
	}
    public String getT() {
		return t;
	}
    public void setT(String tema) {
		this.t = tema;
	}
    public int getNumAccoes() {
		return n_accoes;
	}
    public void setNumAccoes(int a) {
		this.n_accoes = a;
	}
     public Ideia getIdeia() {
		return ideia;
	}
    public void setIdeia(Ideia ideia) {
		this.ideia = ideia;
	}
     public int getPreco() {
		return preco;
	}
    public void setPreco(int preco) {
		this.preco = preco;
	}
    
    public String getTipo(){
        return tipo;
    }
     public String getMensagem(){
        return mensagem;
    }
    public User getUser(){
        return user;
    }
    public void setTipo(String novoTipo){
        tipo = novoTipo;
    }
    public void setMensagem(String novoMensagem){
        mensagem = novoMensagem;
    }
    public void setUser(User u){
        user = u;
    }
    
    
}
