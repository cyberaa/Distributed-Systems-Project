/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.transaccao;

import action.BaseAction;
import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.IdeiaBean;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public class OrdemVendaAction extends BaseAction {

    private String tituloTopico;
    private List<IdeiaBean> listaIdeiabean;

    @Override
    public String execute() {
        setPath("/transaccao/ordemvenda");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (tituloTopico != null && tituloTopico.length() > 0) {
                    List<Ideia> listaIdeias = new ArrayList<>();
                    Object obj = client.getAllIdeiaTopic(tituloTopico);
                    if (obj instanceof String) {
                        String resp = (String) obj;
                        addActionError(resp);
                    } else if(obj instanceof List){
                        listaIdeias = (List<Ideia>)obj;
                        if (listaIdeias.isEmpty()) {
                            addActionError("Não existe ideias para o tópico " + tituloTopico);
                        } else {
                            for (Ideia ideia : listaIdeias) {
                                listaIdeiabean.add(new IdeiaBean(ideia));
                            }
                        }

                    }

                }
            }
        }
        return SUCCESS;
    }

    @Override
    public void prepare() {
        listaIdeiabean = new ArrayList<>();
        tituloTopico = "";
    }

    public String getTituloTopico() {
        return tituloTopico;
    }

    public void setTituloTopico(String tituloTopico) {
        this.tituloTopico = tituloTopico;
    }

    public List<IdeiaBean> getListaIdeiabean() {
        return listaIdeiabean;
    }

    public void setListaIdeiabean(List<IdeiaBean> listaIdeiabean) {
        this.listaIdeiabean = listaIdeiabean;
    }
    

}
