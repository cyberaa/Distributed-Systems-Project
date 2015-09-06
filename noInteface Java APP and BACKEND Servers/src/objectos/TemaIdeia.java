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
public class TemaIdeia implements Serializable {
    private int idTopico;
    private String nomeTopico;
    private String hashTagTopico;
    private int idIdeia;
    private String NomeIdeia;

    public TemaIdeia() {
    }

    public int getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(int idTopico) {
        this.idTopico = idTopico;
    }

    public String getNomeTopico() {
        return nomeTopico;
    }

    public void setNomeTopico(String nomeTopico) {
        this.nomeTopico = nomeTopico;
    }

    public String getHashTagTopico() {
        return hashTagTopico;
    }

    public void setHashTagTopico(String hashTagTopico) {
        this.hashTagTopico = hashTagTopico;
    }

    public int getIdIdeia() {
        return idIdeia;
    }

    public void setIdIdeia(int idIdeia) {
        this.idIdeia = idIdeia;
    }

    public String getNomeIdeia() {
        return NomeIdeia;
    }

    public void setNomeIdeia(String NomeIdeia) {
        this.NomeIdeia = NomeIdeia;
    }
    
    
}
