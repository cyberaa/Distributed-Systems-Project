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
import objectos.TemaIdeia;

/**
 *
 * @author Iris
 */
public class TopicoIdeiaWorker{

    
    public static boolean inserirTopicoIdeia(Connection connection, TemaIdeia temaIdeia) {
        String query = "INSERT INTO topico_ideia";
        query += "(id_topico, id_ideia)";
        query += " VALUES(?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query)) {
            
            stat.setInt(1, temaIdeia.getIdTopico());
            stat.setInt(2, temaIdeia.getIdIdeia());
            
            if(stat.executeUpdate()>0){
                return true;
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean apagarTemaIdeia(Connection connection, TemaIdeia temaIdeia) {
        String sql = "DELETE FROM topico_ideia WHERE id_topico = ? AND id_ideia = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, temaIdeia.getIdTopico());
           ps.setInt(2, temaIdeia.getIdIdeia());
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }
    public static boolean apagarTemaIdeia(Connection connection, int idIdeia) {
        String sql = "DELETE FROM topico_ideia WHERE id_ideia = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idIdeia);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }
    
    public static int getQuantasIdeiasTemUmTopico(Connection connection, int idTopico) {
        String sql = "SELECT COUNT(*) FROM topico_ideia WHERE id_topico = ?";
        int total = -1;
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idTopico);
           ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return total;
    }

    
    public static int getQuantasReferenciasTopicosTemUmaIdeia(Connection connection, int idIdeia) {
        String sql = "SELECT COUNT(*) FROM topico_ideia WHERE id_ideia = ?";
        int total = -1;
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idIdeia);
           ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return total;
    }

    
  /*  public static List<TemaIdeia> getTodasReferenciasTopicosDeUmaIdeia(Connection connection, int idIdeia) {
        
    }*/

    
    public static List<Ideia> getTodasIdeiasDeUmTopicos(Connection connection, int idTopico) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.* FROM topico_ideia as A "+
                        "INNER JOIN ideias as B ON B.id_ideia = A.id_ideia "+
                        "WHERE A.id_topico = ? "+
                        "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, idTopico);
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
