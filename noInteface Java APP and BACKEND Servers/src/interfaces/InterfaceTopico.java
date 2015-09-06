/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import objectos.Tema;
import objectos.User;
import java.util.List;

/**
 *
 * @author Iris
 */
public interface InterfaceTopico {
    public boolean inserirTema(Tema tema);
    
    public boolean apagarTemaByIdTema(int idTema);
    public boolean apagarTodosOsTemaDeUmUtilizadorByIdUtilizador(int idUtilizador);
    public boolean apagarUmTemaUtilizadorByIdUtilizadorAndNomeTema(int idUtilizador ,String nomeTema);
    public boolean apagarUmTemaUtilizadorByNomeTema(String nomeTema);
    
    public boolean existeTema(String nomeTema);
    public boolean existeHasTag(String tag);
    public boolean existeAlgumTemaParaUmUtilizador(int idUtilizador);
    public boolean existeUmTemaParaUmUtilizador(int idUtilizador, String nomeTema);
    
    public Tema getTemaByIdTema(int idTema);
    public Tema getTemaByNomeTema(String nomeTema);
    public Tema getTemaByIdUtilizadorAndNomeTema(int idUtilizador, String nomeTema);
    public List<Tema> getTodosOsTemaDeUmUtilizador(int idUtilizador);
    public List<Tema> getTodosOsTemaDeUmHashTag(String hashtag);
    public List<Tema> getTodosOsTemas();
    
    public List<Tema> getTodosOsTemaNumPeriodo(String data);
    
    public boolean updateNomeTemaByIdTema(int idTema, String novoNomeTema);
    public boolean updateNomeTemaByNomeTema(String nomeTema, String novoNomeTema);
    public boolean updateHashTagByIdTema(int idTema, String novoHashTag);
    public boolean updateHashTagByNomeTema(String nomeTema, String novoHashTag);
    
 
}
