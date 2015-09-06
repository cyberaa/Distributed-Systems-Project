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
import java.util.HashMap;
import java.util.List;
import objectos.Ideia;
import objectos.Transaccao;

/**
 *
 * @author Iris
 */
public class TransaccaoWorker{

    
    public static boolean inserirTransaccao(Connection connection, Transaccao transaccao) {
        String query = "INSERT INTO transacao";
        query += "(id_utilizador,uti_id_utilizador,id_ideia,n_shares,valor_compra,tipocompra)";
        query += " VALUES(?,?,?,?,?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            
            stat.setInt(1, transaccao.getIdVendedor());
            stat.setInt(2, transaccao.getIdComprador());
            stat.setInt(3, transaccao.getIdIdeia());
            stat.setInt(4, transaccao.getNumAccoes());
            stat.setDouble(5, transaccao.getValorTotal());
            stat.setString(6, transaccao.getTipoCompra());
            
            if (stat.executeUpdate() > 0) {
                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaccao.setIdTransaccao( generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean existemOutrosProprietariosDaIdeia(Connection connection, int idIdeia) {
        String sql = "SELECT COUNT(*) AS TOTAL FROM transacao WHERE id_ideia = ?";
        int total = 0;
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idIdeia);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    total= rs.getInt("TOTAL");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(total == 0){
            return false;
        }else{
            return true;
        }
        
    }
    public static List<Transaccao> getTodasTransaccoesUtilizador(Connection connection, int idUtilizador) {
        List<Transaccao> res = new ArrayList<>();
        String sql = "SELECT * FROM transacao WHERE id_utilizador = ? OR uti_id_utilizador = ? ORDER BY data_transaccao DESC LIMIT 10";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            ps.setInt(2, idUtilizador);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Transaccao transaccao = new Transaccao();
                    if (parseResultSet(rs, transaccao)) {
                        res.add(transaccao);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public static List<Ideia> getTodasIdeiasUtilizadorVendeu(Connection connection,int idUtilizador) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.* FROM transacao as A "
                + "INNER JOIN ideias as B ON B.id_ideia = A.id_ideia "
                + "WHERE A.id_utilizador = ? "
                + "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setBoolean(1, true);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Ideia ideia = new Ideia();
                    if (parseResultSet(rs, ideia)) {
                        res.add(ideia);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    
        
    }
    

    
    public static List<Ideia> getTodasIdeiasUtilizadorComprou(Connection connection, int idUtilizador) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.* FROM transacao as A "
                + "INNER JOIN ideias as B ON B.id_ideia = A.id_ideia "
                + "WHERE A.uti_id_utilizador = ? "
                + "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setBoolean(1, true);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Ideia ideia = new Ideia();
                    if (parseResultSet(rs, ideia)) {
                        res.add(ideia);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    
    
    private static boolean parseResultSet(ResultSet rs, Transaccao transaccao) {
        try {
            transaccao.setIdComprador(rs.getInt("uti_id_utilizador"));
            transaccao.setIdIdeia(rs.getInt("id_ideia"));
            transaccao.setIdVendedor(rs.getInt("id_utilizador"));
            transaccao.setNumAccoes(rs.getInt("n_shares"));
            transaccao.setValorTotal(rs.getDouble("valor_compra"));
             transaccao.setDataTransaccao(rs.getTimestamp("data_transaccao"));
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

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
    
    public static List<Ideia> getTodasIdeiasPeloTipoCompra(Connection connection,String tipoCompra) {
        List<Ideia> res = new ArrayList<>();
        String sql = "SELECT B.* FROM transacao as A "
                + "INNER JOIN accao as B ON B.id_ideia = A.id_ideia "
                + "WHERE A.tipocompra = ? "
                + "ORDER BY B.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, tipoCompra);
          
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Ideia ideia = new Ideia();
                    if (parseResultSet(rs, ideia)) {
                        res.add(ideia);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
       
    }
     public static HashMap<String, Integer> getNumAccoesPorIdeiaTemUmUtiliador(Connection connection,int idUtilizador){
         
         return null;
     }
    
}
