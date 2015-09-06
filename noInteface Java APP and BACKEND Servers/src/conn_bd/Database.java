/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conn_bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Iris
 */
public class Database {

    private static Database instancia;
    private static DBObject dbo;
    private static Connection connection;
    public static synchronized Database getDBConnection(DBObject dbo) {
        if (instancia == null) {
            return new Database(dbo);
        }
        return instancia;
    }
    public synchronized Connection getConnection() throws SQLException {
        return createConnection();
    }
    private static Connection createConnection() throws SQLException {
        if(connection!=null && connection.isClosed()){
            connection=null;
        }
        if(connection == null){
            if (dbo.getBaseDadosLigacao().equalsIgnoreCase("ORACLE")) {
                connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/Projecto_BD", "postgres", "basedados");
            } else if (dbo.getBaseDadosLigacao().equalsIgnoreCase("POSTGRES")) {
                String host = dbo.getIp()+":"+dbo.getPorto();
                connection = DriverManager.getConnection("jdbc:postgresql://"+host+"/"+dbo.getNomeBaseDados(), dbo.getUtilizador(), dbo.getPassword());
            }
        }
        return connection;
    }

    private Database(DBObject dbo) {
        this.dbo = dbo;
        loadDriver(dbo.getBaseDadosLigacao());
    }

    private void loadDriver(String nomeBaseDadosLigacao) {
        try {
            if (nomeBaseDadosLigacao.equalsIgnoreCase("ORACLE")) {
                Class.forName("org.postgresql.Driver");
            } else if (nomeBaseDadosLigacao.equalsIgnoreCase("POSTGRES")) {
                Class.forName("org.postgresql.Driver");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
