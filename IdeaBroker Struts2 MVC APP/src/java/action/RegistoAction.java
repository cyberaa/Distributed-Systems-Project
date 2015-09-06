/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import clientWeb.Client;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.net.URLDecoder;
import objectos.User;

public class RegistoAction extends ActionSupport implements Preparable {

    private User user;
    private String username;
    private String passw;
    private String nome;
    private String idade;
    private String pais;
    private String email;

    @Override
    public String execute() throws Exception {

        if (validar()) {
            user.setUsername(URLDecoder.decode(username,"ISO-8859-1"));
            user.setPassw(passw);
            user.setEmail(URLDecoder.decode(email,"ISO-8859-1"));
            user.setIdade(Integer.parseInt(idade));
            user.setPais(URLDecoder.decode(pais,"ISO-8859-1"));
            user.setNome(URLDecoder.decode(nome,"ISO-8859-1"));
            Client client = new Client();
            if (client.connect()) {
                if (client.isRmiIsUp()) {
                    if (!client.isLogged()) {
                        client.setUser(user);
                        if (!client.doRegisto()) {
                            addActionError("Não foi possivel efectuar o registo");
                            return "error";
                        }
                    }
                }else{
                    addActionError("Servidor está em baixo");
                    return "error";
                }
            }else{
                addActionError("Servidor está em baixo");
                return "error";
            }
            return SUCCESS;
        } else{
            return "error";
        } 

    }

    public boolean validar() {
        this.user = new User();
         if ((nome == null) || (nome != null && nome.trim().length() == 0)) {
            addActionError("Campo Nome não pode ser nulo!");
            return false;
        } else if ((pais == null) || (pais != null && pais.trim().length() == 0)) {
            addActionError("Campo Pais não pode ser nulo!");
            return false;
        } else if ((email == null) || (email != null && email.trim().length() == 0)) {
            addActionError("Campo Email não pode ser nulo!");
            return false;
        } else if ((idade == null) || (idade != null && idade.trim().length() == 0)) {
            addActionError("Campo Idade não pode ser nulo!");
            return false;
        } else if (idade != null && idade.trim().length() >= 0) {
            try{
                Integer.parseInt(idade);
            }catch(Exception e){
                addActionError("Campo Idade inválido. Só é permitido numeros!");
                return false;
            }
        } else if ((username == null) || (username != null && username.trim().length() == 0)) {
            addActionError("Campo Username não pode ser nulo!");
            return false;
        } else if ((passw == null) || (passw != null && passw.trim().length() == 0)) {
            addActionError("Campo Password não pode ser nulo!");
            return false;
        } 
        return true;
    }

    @Override
    public void prepare() {
        this.user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
