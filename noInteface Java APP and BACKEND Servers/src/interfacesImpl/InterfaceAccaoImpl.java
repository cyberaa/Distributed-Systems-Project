/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceAccao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.Accao;
import worker.AccaoWorker;

/**
 *
 * @author Iris
 */
public class InterfaceAccaoImpl implements InterfaceAccao{

    private Database database;
    
    public InterfaceAccaoImpl(Database database){
        this.database = database;
    }
    @Override
    public boolean inserirAccaoNaoAutomatica(Accao accao) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.inserirAccaoNaoAutomatica(connection, accao);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
   
    

    @Override
    public boolean apagarAccao(int idUtilizador, int idIdeia, int idAccao) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.apagarAccao(connection, idUtilizador, idIdeia, idAccao);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean updateEstado(int idUtilizador, int idIdeia, int idAccao, String novoEstado) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.updateEstado(connection, idUtilizador, idIdeia, idAccao,novoEstado);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateValorTotalAccoes(int idUtilizador, int idIdeia, int idAccao, double novoValor) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.updateValorTotalAccoes(connection, idUtilizador, idIdeia, idAccao, novoValor);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateNumeroAccoes(int idUtilizador, int idIdeia, int idAccao, int novoNumeroAccoes, String tipo) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.updateNumeroAccoes(connection, idUtilizador, idIdeia, idAccao,novoNumeroAccoes, tipo);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Accao> getTodasAccoesActivas() {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.getTodasAccoesActivas(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Accao> getTodasAccoesVendidas() {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.getTodasAccoesVendidas(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Accao> getTodasAccoesCanceladas() {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.getTodasAccoesCanceladas(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Accao> getTodasAccoesUtilizadorEstado(int idUtilizador, String estado) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.getTodasAccoesUtilizadorEstado(connection,idUtilizador,estado);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Accao> getTodasAccoesIdeiasEstado(int idIdeia, String estado) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.getTodasAccoesIdeiasEstado(connection,idIdeia,estado);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Accao> getTodasAccoesIdeiasEstado2(int idIdeia, String estado) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.getTodasAccoesIdeiasEstado2(connection,idIdeia,estado);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Accao> getIdeiasTipoAutomatico() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean inserirAccaoAutomatica( Accao accao) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.inserirAccaoAutomatica(connection, accao);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarAccao(Accao accao) {
        try(Connection connection = database.getConnection();){
            return AccaoWorker.apagarAccao(connection, accao.getIdUtilizador(), accao.getIdIdeia(), accao.getIdAccao());
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
}
