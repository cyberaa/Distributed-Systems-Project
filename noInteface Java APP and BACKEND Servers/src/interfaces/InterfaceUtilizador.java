/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.HashMap;
import java.util.List;
import objectos.User;

/**
 *
 * @author Iris
 */
public interface InterfaceUtilizador {
    public boolean inserirUtilizador(User user);
    
    public boolean apagarUtilizadorById(int id);
    public boolean apagarUtilizadorByLogin(String login);
    public boolean apagarUtilizadorByEmail(String email);
    
    public boolean existeLogin(String login);
    public User fazLogin(User user);
    
    public User getUtilizadorById(int id);
    public User getUtilizadorByLogin(String login);
    public User getUtilizadorByEmail(String email);
    
    
    public int getSaldoBancario(int idUtilizador);
    
    public boolean updateEstado(int id, boolean estado);
    public boolean updateDeicoins(int id, double valorInvestido);
    public boolean updateDeicoinsCredito(int id, double valorInvestido);
    
    public List<User> allUsers();
    public List<User>allUsersOnline();
    public List<User>allUsersOffline();
   
    
}
