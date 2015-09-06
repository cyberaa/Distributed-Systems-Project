/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public class AllIdeiaTopic  implements Serializable{
    private String nomeTopico;
    private List<Ideia> listaIdeias;

    public AllIdeiaTopic(String nomeTopico) {
        this.nomeTopico = nomeTopico;
        this.listaIdeias = new ArrayList<>();
    }

    public String getNomeTopico() {
        return nomeTopico;
    }

    public void setNomeTopico(String nomeTopico) {
        this.nomeTopico = nomeTopico;
    }

    public List<Ideia> getListaIdeias() {
        return listaIdeias;
    }

    public void setListaIdeias(List<Ideia> listaIdeias) {
        this.listaIdeias = listaIdeias;
    }
    
    
    
    
}
