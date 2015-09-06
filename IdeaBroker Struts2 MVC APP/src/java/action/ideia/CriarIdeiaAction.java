/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.ideia;

import action.BaseAction;
import clientWeb.Client;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import model.IdeiaBean;

/**
 *
 * @author Iris
 */
public class CriarIdeiaAction extends BaseAction {

    private IdeiaBean ideiaBean;
    private String id;
    private String nome;
    private String type;
    private boolean toCreate = false;

    @Override
    public String execute() {
        setPath("/ideia/criarideia");
        Map session = ActionContext.getContext().getSession();
        if (session != null) {
            Client client = (Client) session.get("client");
            if (client != null) {
                setName(client.getUser().getNome());
                if (type != null && type.equals("c")) {
                    if (ideiaBean != null) {
                        validar();
                        if (isToCreate()) {
                            if (client.createIdeia(ideiaBean)) {
                                addActionMessage("Ideia criada com sucesso");
                                ideiaBean.setTitulo("");
                                ideiaBean.setDescricao("");
                                ideiaBean.setTituloTopico("");
                                ideiaBean.setMontante("");
                                ideiaBean.setPercentagem("");
                            } else {
                                addActionError("Não foi possivel criar a Ideia");
                            }
                        }
                    }
                }else{
                    if (ideiaBean != null) {
                        validar();
                        if (isToCreate()) {
                            if (client.createIdeia(ideiaBean)) {
                                addActionMessage("Ideia criada com sucesso");
                                ideiaBean.setTitulo("");
                                ideiaBean.setDescricao("");
                                ideiaBean.setTituloTopico("");
                                ideiaBean.setMontante("");
                                ideiaBean.setPercentagem("");
                            } else {
                                addActionError("Não foi possivel criar a Ideia");
                            }
                        }
                    }
                }

            }
        }
        return SUCCESS;
    }

    public void validar() {
        if (ideiaBean != null) {
            if (ideiaBean.getTituloTopico().length() == 0) {
                addActionError("Titulo do Tópico inválido.");
            } else if (ideiaBean.getTitulo().length() == 0) {
                addActionError("Titulo da ideia inválido.");
            } else if (ideiaBean.getDescricao().length() == 0) {
                addActionError("Descrição da idedia inválido.");
            } else if (ideiaBean.getMontante().length() == 0) {
                addActionError("Montante de investimento na ideia inválido.");
            } else if (ideiaBean.getPercentagem().length() == 0) {
                addActionError("Percentagem de venda automática da ideia inválido.");
            } else {
                setToCreate(true);
            }
        }

    }
    @Override
    public void prepare(){
        if (type != null && type.equals("r")) {
            if(ideiaBean==null){
                ideiaBean = new IdeiaBean();
            }
            ideiaBean.setTituloTopico(nome);
            ideiaBean.setIdTopico(Integer.parseInt(id));
        }
    }
    public boolean isToCreate() {
        return toCreate;
    }

    public void setToCreate(boolean toCreate) {
        this.toCreate = toCreate;
    }

    public IdeiaBean getIdeiaBean() {
        return ideiaBean;
    }

    public void setIdeiaBean(IdeiaBean ideiaBean) {
        this.ideiaBean = ideiaBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
