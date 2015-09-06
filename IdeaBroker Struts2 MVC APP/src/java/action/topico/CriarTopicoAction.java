/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.topico;

import action.BaseAction;
import clientWeb.Client;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import model.TopicoBean;

/**
 *
 * @author Iris
 */
public class CriarTopicoAction extends BaseAction{

    private TopicoBean topicoBean;
    private boolean toCreate;

    @Override
    public String execute() {
        setPath("/topico/criartopico");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (topicoBean != null) {
                    validar();
                    if (isToCreate()) {
                        if (client.createTopic(topicoBean.toTopico())) {
                            addActionMessage("Tópico criado com sucesso");
                            topicoBean.setTitulo("");
                            topicoBean.setHashtag("");
                        } else {
                            addActionError("Não foi possivel criar o tópico");
                        }
                    }
                }

            }
        }
        return SUCCESS;
    }

    public void validar() {
        if(topicoBean!=null){
            if (topicoBean.getTitulo().length() == 0) {
                addActionError("Titulo inválido.");
            } else if (topicoBean.getHashtag().length() == 0) {
                addActionError("Hashtag inválido.");
            } else {
                setToCreate(true);
            }
        }

    }

    @Override
    public void prepare() {
        toCreate = false;
        //topicoBean = new TopicoBean();
    }

    public TopicoBean getTopicoBean() {
        return topicoBean;
    }

    public void setTopicoBean(TopicoBean topicoBean) {
        this.topicoBean = topicoBean;
    }

    public boolean isToCreate() {
        return toCreate;
    }

    public void setToCreate(boolean toCreate) {
        this.toCreate = toCreate;
    }

}
