/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import objectos.Favorito;
import objectos.Ideia;

/**
 *
 * @author Iris
 */
public class FavoritosWorker{

    
    public static boolean inserirFavorito(Connection connection, Favorito favorito) {
        String query = "INSERT INTO favoritos";
        query += "(id_ideia, id_utilizador)";
        query += " VALUES(?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            
            stat.setInt(1, favorito.getIdIdeia());
            stat.setInt(2, favorito.getIdUtilizador());
            
            if(stat.executeUpdate()>0){
                return true;
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    public static boolean apagarUmFavoritoDeUmUtilizador(Connection connection, int idIdeia, int idUtilizador) {
        String sql = "delete FROM favoritos WHERE id_utilizador = ? AND id_ideia = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idUtilizador);
           ps.setInt(2, idIdeia);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    
    public static boolean apagarOsFavoritoDeUmUtilizador(Connection connection, int idUtilizador) {
        String sql = "delete FROM favoritos WHERE id_utilizador = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idUtilizador);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    
    public static List<Ideia> getTodosIdeiasDosFavoritosDeUmUtilizador(Connection connection, int idUtilizador) {
         List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.* FROM favoritos as A "+
                        "INNER JOIN ideias as B ON B.id_ideia = A.id_ideia "+
                        "WHERE A.id_utilizador = ? "+
                        "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, idUtilizador);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Ideia ideia = new Ideia();
                    if(parseResultSet(rs, ideia)){
                        res.add(ideia);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    private static boolean parseResultSet(ResultSet rs, Ideia ideia) {
        try {
            ideia.setId_utilizador(rs.getInt("id_utilizador"));
            ideia.setId_ideia(rs.getInt("id_ideia"));
            ideia.setNome(rs.getString("nome"));
            ideia.setDataCriacao(rs.getTimestamp("data_criacao"));
            ideia.setDescricao(rs.getString("descricao"));
            ideia.setVenda_automatica(rs.getInt("venda_atomatica"));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    
}
