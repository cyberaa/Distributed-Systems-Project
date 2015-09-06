/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import objectos.Ideia;
import objectos.TemaIdeia;

/**
 *
 * @author Iris
 */
public interface InterfaceTopicoIdeia {
    public boolean inserirTopicoIdeia(TemaIdeia temaIdeia);
    
    public boolean apagarTemaIdeia(TemaIdeia temaIdeia);
    public boolean apagarTemaIdeia(int idIdeia);
    
    
    public int getQuantasIdeiasTemUmTopico(int idTopico);
    public int getQuantasReferenciasTopicosTemUmaIdeia(int idIdeia);
    
    public List<TemaIdeia> getTodasReferenciasTopicosDeUmaIdeia(int idIdeia);
    
    public List<Ideia> getTodasIdeiasDeUmTopicos(int idTopico);
    
}
