/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.ideia;

import action.BaseAction;
import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

/**
 *
 * @author Iris
 */
public class ApagarIdeiaAction extends BaseAction {

    private String type;
    private String ref;

    @Override
    public String execute() {
        setPath("/ideia/apagarideia");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (type != null && type.equals("r")) {
                    if (ref != null && ref.length() > 0) {
                        String resp = client.deleteIdeia(Integer.parseInt(ref));
                        if (resp.contains("sucessso")) {
                            addActionMessage(resp);
                            setRef("");
                        } else {
                            addActionError(resp);
                        }
                    }else{
                        addActionError("Referência inválida");
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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
