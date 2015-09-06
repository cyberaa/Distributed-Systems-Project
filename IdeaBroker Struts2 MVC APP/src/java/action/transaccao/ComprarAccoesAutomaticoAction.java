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
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author Iris
 */
public class ComprarAccoesAutomaticoAction extends BaseAction {

    private String ref;
    private String numAccoes;

    @Override
    public String execute() throws RemoteException, SQLException {
        setPath("/transaccao/compraraccoesautomatico");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (ref != null && ref.length() > 0) {
                    if (numAccoes != null && numAccoes.length() > 0) {
                        String resp = client.buySharesIdea(Integer.parseInt(ref), Integer.parseInt(numAccoes));
                        if (resp.contains("sucesso")) {
                            addActionMessage(resp);
                            setRef("");
                            setNumAccoes("");
                        } else {
                            addActionError(resp);
                        }
                    } else {
                        addActionError("O Campo Numero de Accões Não Pode Vazio");
                    }
                } else {
                    addActionError("Referência inválida");
                }

            }
        }
        return SUCCESS;
    }

    public String getNumAccoes() {
        return numAccoes;
    }

    public void setNumAccoes(String numAccoes) {
        this.numAccoes = numAccoes;
    }

   

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
