/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacesImpl;

import conn_bd.Database;
import interfaces.InterfaceFavoritos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import objectos.Favorito;
import objectos.Ideia;
import worker.FavoritosWorker;

/**
 *
 * @author Iris
 */
public class InterfaceFavoritosImpl implements InterfaceFavoritos{

    private Database database;
    
    public InterfaceFavoritosImpl(Database database){
        this.database = database;
    }
    @Override
    public boolean inserirFavorito(Favorito favorito) {
        try(Connection connection = database.getConnection();){
            return FavoritosWorker.inserirFavorito(connection, favorito);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarUmFavoritoDeUmUtilizador(int idIdeia, int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return FavoritosWorker.apagarUmFavoritoDeUmUtilizador(connection, idIdeia, idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean apagarOsFavoritoDeUmUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return FavoritosWorker.apagarOsFavoritoDeUmUtilizador(connection, idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ideia> getTodosIdeiasDosFavoritosDeUmUtilizador(int idUtilizador) {
        try(Connection connection = database.getConnection();){
            return FavoritosWorker.getTodosIdeiasDosFavoritosDeUmUtilizador(connection, idUtilizador);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
}
