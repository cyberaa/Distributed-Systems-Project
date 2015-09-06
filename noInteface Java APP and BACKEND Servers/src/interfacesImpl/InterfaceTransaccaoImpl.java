/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceTransaccao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import objectos.Ideia;
import objectos.Transaccao;
import worker.TransaccaoWorker;

/**
 *
 * @author Iris
 */
public class InterfaceTransaccaoImpl implements InterfaceTransaccao{

    private Database database;
    
    public InterfaceTransaccaoImpl(Database database){
        this.database = database;
    }
    
    @Override
    public boolean inserirTransaccao(Transaccao transaccao) {
        try(Connection connection = database.getConnection();){
            return TransaccaoWorker.inserirTransaccao(connection, transaccao);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Transaccao> getTodasTransaccoesUtilizador(int idUtilizador) {
         try(Connection connection = database.getConnection();){
            return TransaccaoWorker.getTodasTransaccoesUtilizador(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ideia> getTodasIdeiasUtilizadorComprou(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return TransaccaoWorker.getTodasIdeiasUtilizadorComprou(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ideia> getTodasIdeiasUtilizadorVendeu(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return TransaccaoWorker.getTodasIdeiasUtilizadorVendeu(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
        
    }

    @Override
    public List<Ideia> getTodasIdeiasPeloTipoCompra(String tipoCompra) {
         try(Connection connection = database.getConnection();){
            return TransaccaoWorker.getTodasIdeiasPeloTipoCompra(connection,tipoCompra);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HashMap<String, Integer> getNumAccoesPorIdeiaTemUmUtiliador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return TransaccaoWorker.getNumAccoesPorIdeiaTemUmUtiliador(connection,idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existemOutrosProprietariosDaIdeia(int idIdeia) {
        try(Connection connection = database.getConnection();){
            return TransaccaoWorker.existemOutrosProprietariosDaIdeia(connection,idIdeia);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    
    
}
