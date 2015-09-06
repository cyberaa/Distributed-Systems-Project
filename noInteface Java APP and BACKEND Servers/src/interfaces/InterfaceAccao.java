/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.Connection;
import java.util.HashMap;
import objectos.Accao;
import java.util.List;

/**
 *
 * @author Iris
 */
public interface InterfaceAccao {
    public boolean inserirAccaoNaoAutomatica(Accao accao);
    public boolean inserirAccaoAutomatica(Accao accao);
    
    public boolean apagarAccao(int idUtilizador, int idIdeia,int idAccao);
    public boolean apagarAccao(Accao accao);
    
    public boolean updateEstado(int idUtilizador, int idIdeia, int idAccao, String novoEstado);
    public boolean updateValorTotalAccoes(int idUtilizador, int idIdeia, int idAccao, double novoValor);
    public boolean updateNumeroAccoes(int idUtilizador, int idIdeia, int idAccao, int novoNumeroAccoes, String tipo);
    
    public List<Accao> getTodasAccoesActivas();
    public List<Accao> getTodasAccoesVendidas();
    public List<Accao> getTodasAccoesCanceladas();    
    public List<Accao> getTodasAccoesUtilizadorEstado(int idUtilizador, String estado);
    public List<Accao> getTodasAccoesIdeiasEstado(int idIdeia, String estado);
    public List<Accao> getTodasAccoesIdeiasEstado2(int idIdeia, String estado);
    public List<Accao> getIdeiasTipoAutomatico();
    
    
    
   
            
    
}
