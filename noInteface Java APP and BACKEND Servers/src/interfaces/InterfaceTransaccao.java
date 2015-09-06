/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.HashMap;
import objectos.Transaccao;
import java.util.List;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public interface InterfaceTransaccao {
    
    public boolean inserirTransaccao(Transaccao transaccao);
    
    public boolean existemOutrosProprietariosDaIdeia(int idIdeia);
    public List<Transaccao> getTodasTransaccoesUtilizador(int idUtilizador);
    public List<Ideia> getTodasIdeiasUtilizadorComprou(int idUtilizador);
    public List<Ideia> getTodasIdeiasUtilizadorVendeu(int idUtilizador);
    public List<Ideia> getTodasIdeiasPeloTipoCompra(String tipoCompra);
    public HashMap<String, Integer>getNumAccoesPorIdeiaTemUmUtiliador(int idUtilizador);
    
    
    
}
