package objectos;

import java.io.Serializable;
import java.sql.Timestamp;

public class Tema implements Serializable {

    private int idTema = 0;
    private int idUtilizador;
    private String username="";
    private String nome;
    private String hashtag = "";
    private Timestamp dataCriacao;

    public Tema(){
        
    }
    public Tema(int idUtilizador, String nome, Timestamp dataCriacao) {
        this.idUtilizador = idUtilizador;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public Tema(int idUtilizador, String nome, String hashtag, Timestamp dataCriacao) {
        // TODO Auto-generated constructor stub
        this.idUtilizador = idUtilizador;
        this.nome = nome;
        this.hashtag = hashtag;
        this.dataCriacao = dataCriacao;

    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Tema{" + "idTema=" + idTema + ", idUtilizador=" + idUtilizador + ", username=" + username + ", nome=" + nome + ", hashtag=" + hashtag + ", dataCriacao=" + dataCriacao + '}';
    }
    
    
    
    
}
