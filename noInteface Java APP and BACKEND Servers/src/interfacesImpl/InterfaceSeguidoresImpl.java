/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceSeguidores;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.Ideia;
import objectos.Seguidor;
import objectos.User;
import worker.SeguidoresWorker;

/**
 *
 * @author Iris
 */
public class InterfaceSeguidoresImpl implements InterfaceSeguidores{

    private Database database;
    
    public InterfaceSeguidoresImpl(Database database){
        this.database = database;
    }
    @Override
    public boolean inserirSeguidor(Seguidor seguidor) {
        try(Connection connection = database.getConnection();){
            return SeguidoresWorker.inserirSeguidor(connection, seguidor);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarSeguidor(int idIdeia, int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return SeguidoresWorker.apagarSeguidor(connection, idIdeia, idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ideia> getTodosIdeiasSeguidasPeloUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return SeguidoresWorker.getTodosIdeiasSeguidasPeloUtilizador(connection, idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getTodosUtilizadorQueSeguemUmaIdeia(int idIdeia) {
        try(Connection connection = database.getConnection();){
            return SeguidoresWorker.getTodosUtilizadorQueSeguemUmaIdeia(connection, idIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
}
