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
import java.util.Map;

/**
 *
 * @author Iris
 */
public class ApagarTopicoAction extends BaseAction {

    private String type;
    private String nome;

    @Override
    public String execute() {
        setPath("/topico/apagartopico");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (type!=null && type.equals("r")) {
                    if (client.deleteTopic(nome)) {
                        addActionMessage("Tópico removido com sucesso.");
                        setNome("");
                    } else {
                        addActionError("Não foi possivel remover o tópico.");
                    }
                }
            }
        }
        return SUCCESS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
