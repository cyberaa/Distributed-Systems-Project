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
import objectos.Tema;

/**
 *
 * @author Iris
 */
public class TopicoWorker {

    public static boolean inserirTema(Connection connection, Tema tema) {
        String query = "INSERT INTO topico";
        query += "(id_utilizador,nome,hashtag)";
        query += " VALUES(?,?,?)";
        try (PreparedStatement stat = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stat.setInt(1, tema.getIdUtilizador());
            stat.setString(2, tema.getNome());
            stat.setString(3, tema.getHashtag());
            

            if (stat.executeUpdate() > 0) {
                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tema.setIdTema(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean apagarTemaByIdTema(Connection connection, int idTema) {
        String sql = "delete FROM topico WHERE id_topico = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idTema);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean apagarTodosOsTemaDeUmUtilizadorByIdUtilizador(Connection connection, int idUtilizador) {
        String sql = "delete FROM topico WHERE id_utilizador = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean apagarUmTemaUtilizadorByIdUtilizadorAndNomeTema(Connection connection, int idUtilizador, String nomeTema) {
        String sql = "delete FROM topico WHERE id_utilizador = ? AND nome = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            ps.setString(2, nomeTema);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean apagarUmTemaUtilizadorByNomeTema(Connection connection, String nomeTema) {
        String sql = "delete FROM topico WHERE nome = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, nomeTema);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existeTema(Connection connection, String nomeTema) {
        String sql = "SELECT count(*) AS total FROM topico WHERE nome = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, nomeTema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                if (total == 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean existeHasTag(Connection connection, String tag) {
        String sql = "SELECT count(*) AS total FROM topico WHERE hashtag = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                if (total == 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean existeAlgumTemaParaUmUtilizador(Connection connection, int idUtilizador) {
        String sql = "SELECT count(*) AS total FROM topico WHERE id_utilizador = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                if (total == 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean existeUmTemaParaUmUtilizador(Connection connection, int idUtilizador, String nomeTema) {
        String sql = "SELECT count(*) AS total FROM topico WHERE id_utilizador = ? AND nome = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            ps.setString(2, nomeTema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                if (total == 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Tema getTemaByIdTema(Connection connection, int idTema) {
        Tema tema = null;
        String sql = "SELECT * FROM topico WHERE id_topico = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idTema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tema = new Tema();
                if (!parseResultSet(rs, tema)) {
                    tema = null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tema;
    }
    public static Tema getTemaByNomeTema(Connection connection, String nomeTema) {
        Tema tema = null;
        String sql = "SELECT * FROM topico WHERE nome = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, nomeTema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tema = new Tema();
                if (!parseResultSet(rs, tema)) {
                    tema = null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tema;
    }

    public static Tema getTemaByIdUtilizadorAndNomeTema(Connection connection, int idUtilizador, String nomeTema) {
        Tema tema = null;
        String sql = "SELECT * FROM topico WHERE id_utilizador = ? AND nome = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            ps.setString(2, nomeTema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tema = new Tema();
                if (!parseResultSet(rs, tema)) {
                    tema = null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tema;
    }

    public static List<Tema> getTodosOsTemas(Connection connection) {
        List<Tema> res = new ArrayList<>();
        String sql = "SELECT  A.*, B.login FROM topico as A " +
                     "INNER JOIN utilizador as B ON B.id_utilizador = A.id_utilizador "+
                     "ORDER BY A.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Tema tema = new Tema();
                    if (parseResultSet(rs, tema)) {
                        res.add(tema);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public static List<Tema> getTodosOsTemaDeUmUtilizador(Connection connection, int idUtilizador) {
        List<Tema> res = new ArrayList<>();
        String sql = "SELECT  A.*, B.login FROM topico as A " +
                     "INNER JOIN utilizador as B ON B.id_utilizador = A.id_utilizador "+
                     "WHERE A.id_utilizador = ? "+
                     "ORDER BY A.nome";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, idUtilizador);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Tema tema = new Tema();
                    if (parseResultSet(rs, tema)) {
                        res.add(tema);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<Tema> getTodosOsTemaDeUmHashTag(Connection connection, String hashtag) {
        List<Tema> res = new ArrayList<>();
        String sql = "SELECT  A.*, B.login FROM topico as A " +
                     "INNER JOIN utilizador as B ON B.id_utilizador = A.id_utilizador "+
                     "WHERE A.hashtag = ? "+
                     "ORDER BY A.nome";
        
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, hashtag);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Tema tema = new Tema();
                    if (parseResultSet(rs, tema)) {
                        res.add(tema);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<Tema> getTodosOsTemaNumPeriodo(Connection connection, String data) {
        List<Tema> res = new ArrayList<>();
        String sql = "SELECT  A.*, B.login FROM topico as A " +
                     "INNER JOIN utilizador as B ON B.id_utilizador = A.id_utilizador "+
                     "WHERE to_char(A.data_criacao, 'yyyy-MM-dd HH12:MI:SS') like '" + data + "%' "+
                     "ORDER BY A.nome";
        //String sql = "SELECT * FROM topico WHERE to_char(data_criacao, 'yyyy-MM-dd HH12:MI:SS') like '" + data + "%'";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Tema tema = new Tema();
                    if (parseResultSet(rs, tema)) {
                        res.add(tema);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean updateNomeTemaByIdTema(Connection connection, int idTema, String novoNomeTema) {
        String update = "UPDATE topico SET nome = ? WHERE id_topico = ?;";
        try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setString(1, novoNomeTema);
            psupd.setInt(2, idTema);
            if(psupd.executeUpdate() > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateNomeTemaByNomeTema(Connection connection, String nomeTema, String novoNomeTema) {
        String update = "UPDATE topico SET nome = ? WHERE nome = ?;";
       try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setString(1, novoNomeTema);
            psupd.setString(2, nomeTema);
            if(psupd.executeUpdate() > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateHashTagByIdTema(Connection connection, int idTema, String novoHashTag) {
        String update = "UPDATE topico SET hashtag = ? WHERE id_topico = ?";
       try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setString(1, novoHashTag);
            psupd.setInt(2, idTema);
            if(psupd.executeUpdate() > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateHashTagByNomeTema(Connection connection, String nomeTema, String novoHashTag) {
        String update = "UPDATE topico SET hashtag = ? WHERE nome = ?";
       try (
                PreparedStatement psupd = connection.prepareStatement(update);) {
            psupd.setString(1, novoHashTag);
            psupd.setString(2, nomeTema);
            if(psupd.executeUpdate() > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean parseResultSet(ResultSet rs, Tema tema) {
        try {
            tema.setIdTema(rs.getInt("id_topico"));
            tema.setIdUtilizador(rs.getInt("id_utilizador"));
            try{
                tema.setUsername(rs.getString("login"));
            }catch(Exception e){
                
            }
            tema.setNome(rs.getString("nome"));
            tema.setHashtag(rs.getString("hashtag"));
            tema.setDataCriacao(rs.getTimestamp("data_criacao"));
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
