/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceTopicoIdeia;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.Ideia;
import objectos.TemaIdeia;
import suporte.FornecedorDados;
import worker.TopicoIdeiaWorker;

/**
 *
 * @author Iris
 */
public class InterfaceTopicoIdeiaImpl implements InterfaceTopicoIdeia{

    private Database database;
    
    public InterfaceTopicoIdeiaImpl(Database database){
        this.database = database;
    }
    @Override
    public boolean inserirTopicoIdeia(TemaIdeia temaIdeia) {
        try(Connection connection = database.getConnection();){
            return TopicoIdeiaWorker.inserirTopicoIdeia(connection, temaIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getQuantasIdeiasTemUmTopico(int idTopico) {
        try(Connection connection = database.getConnection();){
            return TopicoIdeiaWorker.getQuantasIdeiasTemUmTopico(connection, idTopico);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getQuantasReferenciasTopicosTemUmaIdeia(int idIdeia) {
        try(Connection connection = database.getConnection();){
            return TopicoIdeiaWorker.getQuantasReferenciasTopicosTemUmaIdeia(connection, idIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TemaIdeia> getTodasReferenciasTopicosDeUmaIdeia(int idIdeia) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Ideia> getTodasIdeiasDeUmTopicos(int idTopico) {
        try(Connection connection = database.getConnection();){
            return TopicoIdeiaWorker.getTodasIdeiasDeUmTopicos(connection, idTopico);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean apagarTemaIdeia(TemaIdeia temaIdeia) {
        try(Connection connection = database.getConnection();){
            return TopicoIdeiaWorker.apagarTemaIdeia(connection, temaIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarTemaIdeia(int idIdeia) {
        try(Connection connection = database.getConnection();){
            return TopicoIdeiaWorker.apagarTemaIdeia(connection, idIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
}
