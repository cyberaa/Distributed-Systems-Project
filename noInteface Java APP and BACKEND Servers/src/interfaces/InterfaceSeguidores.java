/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import objectos.Ideia;
import objectos.Seguidor;
import objectos.User;

/**
 *
 * @author Iris
 */
public interface InterfaceSeguidores {
    public boolean inserirSeguidor(Seguidor seguidor);
    public boolean apagarSeguidor(int idIdeia,int idUtilizador);
    public List<Ideia> getTodosIdeiasSeguidasPeloUtilizador(int idUtilizador);
    public List<User> getTodosUtilizadorQueSeguemUmaIdeia(int idIdeia);
    
}
