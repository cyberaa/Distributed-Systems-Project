/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.topico;

import action.BaseAction;
import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.TopicoBean;
import objectos.Tema;

/**
 *
 * @author Iris
 */
public class ListarTopicosAction extends BaseAction {

    private List<TopicoBean> listaTopicos;
    private String errorMessage;

    @Override
    public String execute() {
        setPath("/topico/listartopicos");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                List<Tema> lista = client.getAllTopics();
                if(lista!=null){
                    for (Tema tema : lista) {
                        listaTopicos.add(new TopicoBean(tema));
                    }
                }
            }
            if (listaTopicos.isEmpty()) {
                addActionError("Não existem tópicos.");
            }

        }
        return SUCCESS;
    }
     @Override
    public void prepare() {
        listaTopicos = new ArrayList<>();
    }
    public List<TopicoBean> getListaTopicos() {
        return listaTopicos;
    }

    public void setListaTopicos(List<TopicoBean> listaTopicos) {
        this.listaTopicos = listaTopicos;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
