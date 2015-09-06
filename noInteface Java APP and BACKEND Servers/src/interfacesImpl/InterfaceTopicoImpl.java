/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceTopico;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.Tema;
import worker.TopicoWorker;

/**
 *
 * @author Iris
 */
public class InterfaceTopicoImpl implements InterfaceTopico{
    private Database database;
    
    public InterfaceTopicoImpl(Database database){
        this.database = database;
    }

    @Override
    public boolean inserirTema(Tema tema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.inserirTema(connection,tema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarTemaByIdTema(int idTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.apagarTemaByIdTema(connection,idTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarTodosOsTemaDeUmUtilizadorByIdUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.apagarTodosOsTemaDeUmUtilizadorByIdUtilizador(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarUmTemaUtilizadorByIdUtilizadorAndNomeTema(int idUtilizador, String nomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.apagarUmTemaUtilizadorByIdUtilizadorAndNomeTema(connection,idUtilizador,nomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarUmTemaUtilizadorByNomeTema(String nomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.apagarUmTemaUtilizadorByNomeTema(connection,nomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existeTema(String nomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.existeTema(connection,nomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existeHasTag(String tag) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.existeHasTag(connection,tag);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existeAlgumTemaParaUmUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.existeAlgumTemaParaUmUtilizador(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existeUmTemaParaUmUtilizador(int idUtilizador, String nomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.existeUmTemaParaUmUtilizador(connection,idUtilizador,nomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Tema getTemaByIdTema(int idTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTemaByIdTema(connection,idTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tema getTemaByIdUtilizadorAndNomeTema(int idUtilizador, String nomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTemaByIdUtilizadorAndNomeTema(connection,idUtilizador,nomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tema> getTodosOsTemaDeUmUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTodosOsTemaDeUmUtilizador(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tema> getTodosOsTemaDeUmHashTag(String hashtag) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTodosOsTemaDeUmHashTag(connection,hashtag);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Tema> getTodosOsTemas() {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTodosOsTemas(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Tema> getTodosOsTemaNumPeriodo(String data) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTodosOsTemaNumPeriodo(connection,data);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateNomeTemaByIdTema(int idTema, String novoNomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.updateNomeTemaByIdTema(connection,idTema,novoNomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false ;
    }

    @Override
    public boolean updateNomeTemaByNomeTema(String nomeTema, String novoNomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.updateNomeTemaByNomeTema(connection,nomeTema,novoNomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateHashTagByIdTema(int idTema, String novoHashTag) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.updateHashTagByIdTema(connection,idTema,novoHashTag);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateHashTagByNomeTema(String nomeTema, String novoHashTag) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.updateHashTagByNomeTema(connection,nomeTema,novoHashTag);
        }catch(SQLException e){
            e.printStackTrace();
            
        }
        return false;
        
    }

    @Override
    public Tema getTemaByNomeTema(String nomeTema) {
        try(Connection connection = database.getConnection();){
            return TopicoWorker.getTemaByNomeTema(connection,nomeTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
   
    
}
