package objectos;

import java.io.Serializable;
import java.sql.Timestamp;


public class Ideia implements Serializable{
    
    private int id_ideia = 0;
    private int id_utilizador;
    private int id_topico;
    private String nomeTopico;
    private String username;
    private String nome;
    private String descricao;
    private Timestamp dataCriacao;
    
    public Ideia() {
    }

    public int getId_ideia() {
        return id_ideia;
    }

    public void setId_ideia(int id_ideia) {
        this.id_ideia = id_ideia;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    private int venda_automatica;

    public int getId_utilizador() {
        return id_utilizador;
    }

    public void setId_utilizador(int id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getVenda_automatica() {
        return venda_automatica;
    }

    public void setVenda_automatica(int venda_automatica) {
        this.venda_automatica = venda_automatica;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId_topico() {
        return id_topico;
    }

    public void setId_topico(int id_topico) {
        this.id_topico = id_topico;
    }

    public String getNomeTopico() {
        return nomeTopico;
    }

    public void setNomeTopico(String nomeTopico) {
        this.nomeTopico = nomeTopico;
    }

    @Override
    public String toString() {
        return "Ideia{" + "id_ideia=" + id_ideia + ", id_utilizador=" + id_utilizador + ", id_topico=" + id_topico + ", nomeTopico=" + nomeTopico + ", username=" + username + ", nome=" + nome + ", descricao=" + descricao + ", dataCriacao=" + dataCriacao + ", venda_automatica=" + venda_automatica + '}';
    }

    
   

    public Ideia(int id_utilizador, String nome, String descricao, int venda_automatica) {
        this.id_utilizador = id_utilizador;
        this.nome = nome;
        this.descricao = descricao;
        this.venda_automatica = venda_automatica;
    }

    
        
	
}