/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public interface InterfaceIdeia {
    
    public boolean inserirIdeia(Ideia ideia);
    public boolean apagarIdeiaByIdIdeia(int idIdeia);
    public boolean apagarIdeiaByIdIdeiaIdUtilizador(int idIdeia, int idUtilizador);
    public boolean apagarTodosAsIdeiasDeUmUtilizadorByIdUtilizador(int idUtilizador);
    public boolean apagarUmaIdeiaByNomeIdeia(int idUtilizador ,String nomeIdeia);
    public List<Ideia> getTodasAsIdeiasDeUmUtilizadorByIdUtilizador(int idUtilizador);
    public List<Ideia> getTodasAsIdeiasDeUmTopico(int idTema);
    public Ideia getUmaIdeia(int idIdeia);
    public Ideia getUmaIdeiaDeUmTopico(int idIdeia, int idTema);
    
     
    
    
    
    
    
    
    
    
}
