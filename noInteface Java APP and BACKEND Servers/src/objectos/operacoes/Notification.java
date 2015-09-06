/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;

/**
 *
 * @author Iris
 */
public class Notification implements Serializable{
    private String texto;

    public Notification() {
    }

    public Notification(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    
}
