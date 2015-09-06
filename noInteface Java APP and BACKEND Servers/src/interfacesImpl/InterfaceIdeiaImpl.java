/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceIdeia;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.Ideia;
import worker.IdeiaWorker;


/**
 *
 * @author Iris
 */
public class InterfaceIdeiaImpl implements InterfaceIdeia{
    private Database database;
    
    public InterfaceIdeiaImpl(Database database){
        this.database = database;
    }

    @Override
    public boolean inserirIdeia(Ideia ideia) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.inserirIdeia(connection, ideia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarIdeiaByIdIdeia(int idIdeia) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.apagarIdeiaByIdIdeia(connection, idIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarTodosAsIdeiasDeUmUtilizadorByIdUtilizador(int idUtilizador) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean apagarUmaIdeiaByNomeIdeia(int idUtilizador, String nomeIdeia) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Ideia> getTodasAsIdeiasDeUmUtilizadorByIdUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.getTodasAsIdeiasDeUmUtilizadorByIdUtilizador(connection, idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ideia> getTodasAsIdeiasDeUmTopico(int idTema) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.getTodasAsIdeiasDeUmTopico(connection, idTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ideia getUmaIdeia(int idIdeia) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.getUmaIdeia(connection, idIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ideia getUmaIdeiaDeUmTopico(int idIdeia, int idTema) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.getUmaIdeiaDeUmTopico(connection, idIdeia, idTema);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean apagarIdeiaByIdIdeiaIdUtilizador(int idIdeia, int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return IdeiaWorker.apagarIdeiaByIdIdeiaIdUtilizador(connection, idIdeia,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
