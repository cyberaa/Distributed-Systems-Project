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
import objectos.Ideia;
import objectos.Seguidor;
import objectos.User;

/**
 *
 * @author Iris
 */
public class SeguidoresWorker{

    
    public static boolean inserirSeguidor(Connection connection, Seguidor seguidor) {
        String query = "INSERT INTO seguidores";
        query += "(id_ideia, id_utilizador)";
        query += " VALUES(?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            
            stat.setInt(1, seguidor.getIdIdeia());
            stat.setInt(2, seguidor.getIdUtilizador());
            
            if(stat.executeUpdate()>0){
                return true;
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    public static boolean apagarSeguidor(Connection connection, int idIdeia, int idUtilizador) {
        String sql = "delete FROM seguidores WHERE id_utilizador = ? AND id_ideia = ?";
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

    
    public static List<Ideia> getTodosIdeiasSeguidasPeloUtilizador(Connection connection, int idUtilizador) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.* FROM seguidores as A "+
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

    
    public static List<User> getTodosUtilizadorQueSeguemUmaIdeia(Connection connection, int idIdeia) {
        List<User> res = new ArrayList<>();
        String sql = "SELECT B.* FROM seguidores as A "+
                        "INNER JOIN utilizadores as B ON B.id_utilizador = A.id_utilizador "+
                        "WHERE A.id_ideia = ? "+
                        "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, idIdeia);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    User user = new User();
                    if(parseResultSet(rs, user)){
                        res.add(user);
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
    private static boolean parseResultSet(ResultSet rs, User user) {
        try {
            user.setIdUtilizador(rs.getInt("id_utilizador"));
            user.setDeicoins(rs.getDouble("deicoins"));
            user.setEmail(rs.getString("email"));
            user.setIdade(rs.getInt("idade"));
            user.setNome(rs.getString("nome"));
            user.setPais(rs.getString("pais"));
            user.setPassw(rs.getString("pass"));
            user.setUsername(rs.getString("login"));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    
}
