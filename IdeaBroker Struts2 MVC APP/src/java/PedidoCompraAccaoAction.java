/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import action.BaseAction;
import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.AccaoBean;
import model.IdeiaBean;
import objectos.Accao;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public class PedidoCompraAccaoAction extends BaseAction {

    private String ref;
    private List<AccaoBean> listaIdeiabean;

    @Override
    public String execute() throws RemoteException, SQLException {
         setPath("/transaccao/pedidocompraaccao");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (ref != null && ref.length() > 0) {
                    List<Accao> listaIdeias = new ArrayList<>();
                    List<Accao> listaAccao = client.listaPedidosIdeia(Integer.parseInt(ref));
                    
                        if (listaAccao.isEmpty()) {
                            addActionError("Não existe ideias para o tópico " + ref);
                        } else {
                            for (Accao accao : listaIdeias) {
                                listaIdeiabean.add(new AccaoBean(accao));
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
        ref = "";
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public List<AccaoBean> getListaIdeiabean() {
        return listaIdeiabean;
    }

    public void setListaIdeiabean(List<AccaoBean> listaIdeiabean) {
        this.listaIdeiabean = listaIdeiabean;
    }

    
    

}
