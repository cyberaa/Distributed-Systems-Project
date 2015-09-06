/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos;

/**
 *
 * @author Iris
 */
public class Favorito {
    private int idIdeia;
    private String nomeIdeia;
    private int idUtilizador;
    private String nomeUtilizador;

    public Favorito() {
    }

    public int getIdIdeia() {
        return idIdeia;
    }

    public void setIdIdeia(int idIdeia) {
        this.idIdeia = idIdeia;
    }

    public String getNomeIdeia() {
        return nomeIdeia;
    }

    public void setNomeIdeia(String nomeIdeia) {
        this.nomeIdeia = nomeIdeia;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNomeUtilizador() {
        return nomeUtilizador;
    }

    public void setNomeUtilizador(String nomeUtilizador) {
        this.nomeUtilizador = nomeUtilizador;
    }

    @Override
    public String toString() {
        return "Favorito{" + "idIdeia=" + idIdeia + ", nomeIdeia=" + nomeIdeia + ", idUtilizador=" + idUtilizador + ", nomeUtilizador=" + nomeUtilizador + '}';
    }
    
    
    
}
