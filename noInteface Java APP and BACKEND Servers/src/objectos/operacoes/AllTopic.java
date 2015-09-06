/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectos.operacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import objectos.Tema;

/**
 *
 * @author Iris
 */
public class AllTopic implements Serializable{
    private List<Tema> listaTemas;
    public AllTopic() {
        this.listaTemas = new ArrayList<>();
    }
    public List<Tema> getListaTemas() {
        return listaTemas;
    }

    public void setListaTemas(List<Tema> listaTemas) {
        this.listaTemas = listaTemas;
    }
}
