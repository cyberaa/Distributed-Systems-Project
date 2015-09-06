/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceUtilizador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.User;
import worker.UtilizadorWorker;

/**
 *
 * @author Iris
 */
public class InterfaceUtilizadorImpl implements InterfaceUtilizador{
    private Database database;
    
    public InterfaceUtilizadorImpl(Database database){
        this.database = database;
    }
    @Override
    public boolean inserirUtilizador(User user) {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.inserirUtilizador(connection,user);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarUtilizadorById(int id) {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.apagarUtilizadorById(connection,id);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarUtilizadorByLogin(String login) {
         try(Connection connection = database.getConnection();){
             return UtilizadorWorker.apagarUtilizadorByLogin(connection,login);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarUtilizadorByEmail(String email) {
         try(Connection connection = database.getConnection();){
            return UtilizadorWorker.apagarUtilizadorByEmail(connection,email);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existeLogin(String login) {
         try(Connection connection = database.getConnection();){
            return UtilizadorWorker.existeLogin(connection,login);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUtilizadorById(int id) {
         try(Connection connection = database.getConnection();){
            return UtilizadorWorker.getUtilizadorById(connection,id);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUtilizadorByLogin(String login) {
         try(Connection connection = database.getConnection();){
            return UtilizadorWorker.getUtilizadorByLogin(connection,login);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUtilizadorByEmail(String email) {
         try(Connection connection = database.getConnection();){
            return UtilizadorWorker.getUtilizadorByEmail(connection,email);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;    
    }

    @Override
    public boolean updateEstado(int id, boolean estado) {
         try(Connection connection = database.getConnection();){
            return UtilizadorWorker.updateEstado(connection,id,estado);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> allUsers() {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.allUsers(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> allUsersOnline() {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.allUsersOnline(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> allUsersOffline() {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.allUsersOffline(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getSaldoBancario(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.getSaldoBancario(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User fazLogin(User user) {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.fazLogin(connection,user);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateDeicoins(int id, double valorInvestido) {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.updateDeicoins(connection,id,valorInvestido);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean updateDeicoinsCredito(int id, double valorInvestido) {
        try(Connection connection = database.getConnection();){
            return UtilizadorWorker.updateDeicoinsCredito(connection,id,valorInvestido);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
}
