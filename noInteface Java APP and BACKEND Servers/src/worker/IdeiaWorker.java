/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import objectos.Ideia;


/**
 *
 * @author Iris
 */
public class IdeiaWorker{
    
    
    public static boolean inserirIdeia(Connection connection,Ideia ideia) {
        String query = "INSERT INTO ideias";
        query += "(id_utilizador,nome,venda_atomatica,descricao)";
        query += " VALUES(?,?,?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stat.setInt(1, ideia.getId_utilizador());
            stat.setString(2, ideia.getNome());
            stat.setInt(3, ideia.getVenda_automatica());
            stat.setString(4, ideia.getDescricao());
            

            if (stat.executeUpdate() > 0) {
                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ideia.setId_ideia(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    public static boolean apagarIdeiaByIdIdeia(Connection connection,int idIdeia) {
        String sql = "delete FROM ideias WHERE id_ideia = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idIdeia);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean apagarIdeiaByIdIdeiaIdUtilizador(Connection connection,int idIdeia, int idUtilizador) {
        String sql = "delete FROM ideias WHERE id_ideia = ? and id_utilizador = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idIdeia);
            ps.setInt(2, idUtilizador);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean apagarTodosAsIdeiasDeUmUtilizadorByIdUtilizador(Connection connection,int idUtilizador) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public static boolean apagarUmaIdeiaByNomeIdeia(Connection connection,int idUtilizador, String nomeIdeia) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
   public static List<Ideia> getTodasAsIdeiasDeUmUtilizadorByIdUtilizador(Connection connection,int idUtilizador) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT A.*, B.login FROM ideias as A "+
                        "INNER JOIN utilizador as B ON B.id_utilizador = A.id_utilizador "+
                        "WHERE A.id_utilizador = ? "+
                        "ORDER BY A.nome";
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

    
    public static List<Ideia> getTodasAsIdeiasDeUmTopico(Connection connection,int idTema) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.*, C.login FROM topico_ideia as A "+
                        "INNER JOIN ideia as B ON B.id_ideia = A.id_ideia "+
                        "INNER JOIN utilizador as C ON C.id_utilizador = B.id_utilizador "+
                        "WHERE A.id_topico = ? "+
                        "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, idTema);
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

    
    public static Ideia getUmaIdeia(Connection connection,int idIdeia) {
        Ideia ideia = null;
        String sql = "SELECT A.*, B.login FROM ideias as A "+
                        "INNER JOIN utilizador as B ON B.id_utilizador = A.id_utilizador "+
                        "WHERE A.id_ideia = ? ";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, idIdeia);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    ideia = new Ideia();
                    parseResultSet(rs, ideia);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ideia;
    }

    
    public static Ideia getUmaIdeiaDeUmTopico(Connection connection,int idIdeia, int idTema) {
        Ideia ideia = null;
        String sql = "SELECT B.*, C.login FROM topico_ideia as A "+
                        "INNER JOIN ideia as B ON B.id_ideia = A.id_ideia "+
                        "INNER JOIN utilizador as C ON C.id_utilizador = B.id_utilizador "+
                        "WHERE A.id_ideia = ? AND A.id_topico = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, idIdeia);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    ideia = new Ideia();
                    parseResultSet(rs, ideia);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ideia;
    }
    private static boolean parseResultSet(ResultSet rs, Ideia ideia) {
        try {
            ideia.setId_utilizador(rs.getInt("id_utilizador"));
            ideia.setId_ideia(rs.getInt("id_ideia"));
            ideia.setNome(rs.getString("nome"));
            try{
                ideia.setUsername(rs.getString("login"));
            }catch(Exception e){
                
            }
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
