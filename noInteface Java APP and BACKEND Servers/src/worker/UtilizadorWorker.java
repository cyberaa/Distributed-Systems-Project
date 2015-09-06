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
import java.util.logging.Level;
import java.util.logging.Logger;
import objectos.User;


/**
 *
 * @author Iris
 */
public class UtilizadorWorker {
    
    public static boolean inserirUtilizador(Connection connection, User user) {
        PreparedStatement stat=null;
        String query = "INSERT INTO utilizador";
        query += "(nome,login,pass,pais,email,idade)";
        query += " VALUES(?,?,?,?,?,?)";
        try {
            stat = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            stat.setString(1, user.getNome());
            stat.setString(2, user.getUsername());
            stat.setString(3, user.getPassw());
            stat.setString(4, user.getPais());
            stat.setString(5, user.getEmail());
            stat.setInt(6, user.getIdade());
            
            if(stat.executeUpdate()>0){
                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setIdUtilizador(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UtilizadorWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    
    public static boolean apagarUtilizadorById(Connection connection,int id) {
       String sql = "delete FROM Utilizador WHERE id_utilizador = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, id);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }
     public static boolean apagarUtilizadorByLogin(Connection connection,String login) {
       String sql = "delete FROM Utilizador WHERE login = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setString(1, login);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

 

    
    public static boolean apagarUtilizadorByEmail(Connection connection,String email) {
       String sql = "delete FROM Utilizador WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setString(1, email);
           if(ps.executeUpdate()>0){
               return true;
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }
    
    public static boolean existeLogin(Connection connection,String login) {
        String sql = "SELECT count(*) AS total FROM Utilizador WHERE login = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, login);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                if (total == 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true ;
    }
    
    public static User fazLogin(Connection connection,User user) {
        String sql = "SELECT * FROM Utilizador WHERE login = ? AND pass = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassw());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                parseResultSet(rs, user);
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public static User getUtilizadorById(Connection connection,int id) {
        User user = null;
        String sql = "SELECT * FROM Utilizador WHERE id_utilizador = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    user = new User();
                    if(!parseResultSet(rs, user)){
                        user=null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    
    public static User getUtilizadorByLogin(Connection connection,String login) {
        User user = null;
        String sql = "SELECT * FROM Utilizador WHERE login = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    user = new User();
                    if(!parseResultSet(rs, user)){
                        user=null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    
    public static User getUtilizadorByEmail(Connection connection, String email) {
        User user = null;
        String sql = "SELECT * FROM Utilizador WHERE email = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    user = new User();
                    if(!parseResultSet(rs, user)){
                        user=null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
   
    public static boolean updateEstado(Connection connection, int id, boolean estado) {
        String update = "UPDATE Utilizador SET status = ? WHERE id_utilizador = ?;";
        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setBoolean(1, estado);
            psupd.setInt(2, id);
            if(psupd.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateDeicoins(Connection connection, int id, double valorInvestido) {
        String update = "UPDATE Utilizador SET deicoins = (deicoins - ?) WHERE id_utilizador = ?";

        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setDouble(1, valorInvestido);
            psupd.setInt(2, id);
            if(psupd.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean updateDeicoinsCredito(Connection connection, int id, double valorInvestido) {
        String update = "UPDATE Utilizador SET deicoins = (deicoins + ?) WHERE id_utilizador = ?";

        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setDouble(1, valorInvestido);
            psupd.setInt(2, id);
            if(psupd.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public static List<User> allUsersOnline(Connection connection) {
         List<User> res = new ArrayList<>();
        String sql = "SELECT * FROM utilizador WHERE status = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setBoolean(1, true);
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

    
    public static List<User> allUsersOffline(Connection connection) {
        List<User> res = new ArrayList<>();
        String sql = "SELECT * FROM utilizador WHERE status = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setBoolean(1, false);
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
    public static List<User> allUsers(Connection connection) {
        List<User> res = new ArrayList<>();
        String sql = "SELECT * FROM utilizador";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
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
            user.setOnline(rs.getBoolean("status"));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    public static int getSaldoBancario(Connection connection,int idUtilizador) {
        String sql = "SELECT deicoins FROM utilizadores WHERE id_utilizador = ?";
        int total = -1;
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
           ps.setInt(1, idUtilizador);
           ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("deicoins");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return total;
    }
}
