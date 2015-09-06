/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

/**
 *
 * @author Iris
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import action.BaseAction;
import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.TopicoBean;
import model.TransaccaoBean;
import objectos.Tema;
import objectos.Transaccao;

/**
 *
 * @author Iris
 */
public class MeuHistoricoAction extends BaseAction {

    private List<TransaccaoBean> listaHistorico;
    private String labelSucess;
    private String labelError;

    @Override
    public String execute() {
        setPath("/meuHistorico");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                List<Transaccao> lista = client.getMyHistoric();
                if (lista != null) {
                    for (Transaccao transaccao : lista) {
                        listaHistorico.add(new TransaccaoBean(transaccao));
                        
                    }
                }
            }
            if (listaHistorico.isEmpty()) {
                addActionError("Não existem histórico de transacções.");
            }

        }
        return SUCCESS;
    }

    @Override
    public void prepare() {
        listaHistorico = new ArrayList<>();
    }

    public List<TransaccaoBean> getListaTopicos() {
        return listaHistorico;
    }

    public void setListaTopicos(List<TransaccaoBean> listaTopicos) {
        this.listaHistorico = listaTopicos;
    }

    public String getLabelSucess() {
        return labelSucess;
    }

    public void setLabelSucess(String labelSucess) {
        this.labelSucess = labelSucess;
    }

    public String getLabelError() {
        return labelError;
    }

    public void setLabelError(String labelError) {
        this.labelError = labelError;
    }

}
