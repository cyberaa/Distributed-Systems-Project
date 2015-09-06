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
import objectos.Accao;
import suporte.Values;

/**
 *
 * @author Iris
 */
public class AccaoWorker{

    
    public static boolean inserirAccaoNaoAutomatica(Connection connection, Accao accao) {
        String query = "INSERT INTO accao";
        query += "(id_utilizador,id_ideia,num_shares,valor,tipoCompra)";
        query += " VALUES(?,?,?,?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            
            stat.setInt(1, accao.getIdUtilizador());
            stat.setInt(2, accao.getIdIdeia());
            stat.setInt(3, accao.getNumAccoes());
            stat.setDouble(4, accao.getValorTotal());
            stat.setString(5, Values.NAOAUTOMATICO);
            
            if (stat.executeUpdate() > 0) {
                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        accao.setIdAccao(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public static boolean inserirAccaoAutomatica(Connection connection, Accao accao) {
        String query = "INSERT INTO accao";
        query += "(id_utilizador,id_ideia,num_shares,valor)";
        query += " VALUES(?,?,?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            
            stat.setInt(1, accao.getIdUtilizador());
            stat.setInt(2, accao.getIdIdeia());
            stat.setInt(3, accao.getNumAccoes());
            stat.setDouble(4, accao.getValorTotal());
            if (stat.executeUpdate() > 0) {
                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        accao.setIdAccao(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean apagarAccao(Connection connection, int idUtilizador, int idIdeia, int idAccao) {
        String sql = "delete FROM accao WHERE id_utilizador = ? AND id_ideia = ? AND id_accao = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idUtilizador);
           ps.setInt(2, idIdeia);
           ps.setInt(3, idAccao);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }
    
    public static boolean updateEstado(Connection connection, int idUtilizador, int idIdeia, int idAccao, String novoEstado) {
        String update = "UPDATE accao SET estado = ? WHERE id_utilizador = ? AND id_ideia = ? AND id_accao = ?";
        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setString(1, novoEstado);
            psupd.setInt(2, idUtilizador);
            psupd.setInt(3, idIdeia);
            psupd.setInt(4, idAccao);
            if(psupd.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static boolean updateValorTotalAccoes(Connection connection, int idUtilizador, int idIdeia, int idAccao, double novoValor) {
        String update = "UPDATE accao SET valor = ? WHERE id_utilizador = ? AND id_ideia = ? AND id_accao = ?";
        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setDouble(1, novoValor);
            psupd.setInt(2, idUtilizador);
            psupd.setInt(3, idIdeia);
            psupd.setInt(4, idAccao);
            if(psupd.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static boolean updateNumeroAccoes(Connection connection, int idUtilizador, int idIdeia, int idAccao, int novoNumeroAccoes, String tipo) {
        String update = "";
        if(tipo.equals(Values.CREDITO)){
            update = "UPDATE accao SET num_shares = (num_shares + ?) WHERE id_utilizador = ? AND id_ideia = ? AND id_accao = ?";
        }else if(tipo.equals(Values.DEBITO)){
            update = "UPDATE accao SET num_shares = (num_shares - ?) WHERE id_utilizador = ? AND id_ideia = ? AND id_accao = ?";
        } 
        
        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setInt(1, novoNumeroAccoes);
            psupd.setInt(2, idUtilizador);
            psupd.setInt(3, idIdeia);
            psupd.setInt(4, idAccao);
            if(psupd.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static List<Accao> getTodasAccoesActivas(Connection connection) {
         List<Accao> res = new ArrayList<>();
        String sql = "SELECT * FROM accao WHERE estado = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, Values.ACTIVO);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Accao accao = new Accao();
                    if(parseResultSet(rs, accao)){
                        res.add(accao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    public static List<Accao> getTodasAccoesVendidas(Connection connection) {
        List<Accao> res = new ArrayList<>();
        String sql = "SELECT * FROM accao WHERE estado = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, Values.VENDIDA);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Accao accao = new Accao();
                    if(parseResultSet(rs, accao)){
                        res.add(accao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    public static List<Accao> getTodasAccoesCanceladas(Connection connection) {
        List<Accao> res = new ArrayList<>();
        String sql = "SELECT * FROM accao WHERE estado = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, Values.CANCELADA);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Accao accao = new Accao();
                    if(parseResultSet(rs, accao)){
                        res.add(accao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    public static List<Accao> getTodasAccoesUtilizadorEstado(Connection connection, int idUtilizador, String estado) {
        List<Accao> res = new ArrayList<>();
        String sql = "SELECT * FROM accao WHERE estado = ? AND id_utilizador = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, estado);
            ps.setInt(2,idUtilizador);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Accao accao = new Accao();
                    if(parseResultSet(rs, accao)){
                        res.add(accao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    public static List<Accao> getTodasAccoesIdeiasEstado(Connection connection, int idIdeia, String estado) {
        List<Accao> res = new ArrayList<>();
        String sql = "SELECT * FROM accao WHERE estado = ? AND id_ideia = ? and ORDER BY valor ASC";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, estado);
            ps.setInt(2,idIdeia);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Accao accao = new Accao();
                    if(parseResultSet(rs, accao)){
                        res.add(accao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
     public static List<Accao> getTodasAccoesIdeiasEstado2(Connection connection, int idIdeia, String estado) {
        List<Accao> res = new ArrayList<>();
        String sql = "SELECT * FROM accao WHERE estado = ? AND id_ideia = ? AND tipocompra = ? ORDER BY valor ASC";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, estado);
            ps.setInt(2,idIdeia);
            ps.setString(3,"AUTOMATICO");
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Accao accao = new Accao();
                    if(parseResultSet(rs, accao)){
                        res.add(accao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    private static boolean parseResultSet(ResultSet rs, Accao accao) {
        try {
            accao.setIdAccao(rs.getInt("id_accao"));
            accao.setIdUtilizador(rs.getInt("id_utilizador"));
            accao.setIdIdeia(rs.getInt("id_ideia"));
            accao.setNumAccoes(rs.getInt("num_shares"));
            accao.setValorTotal(rs.getDouble("valor"));
            accao.setEstado(rs.getString("estado"));
            accao.setTipoCompra(rs.getString("tipocompra"));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
}
