/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import conn_bd.DBObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Iris
 */
public class LeituraFicheiro {

    public static DBObject getParametrosConfiguracaoBD(String filename) {
        DBObject dbo = null;
        if (checkFile(filename)) {
            try {
                Scanner scanner = new Scanner(new FileInputStream(filename));
                try {
                    dbo = new DBObject();
                    while (scanner.hasNextLine()) {
                        String text = scanner.nextLine();
                        if (!text.startsWith("#")) {
                            String[] dados = text.split("=");
                            if (dados.length == 2) {
                                String key = dados[0];
                                switch (key) {
                                    case "host":
                                        dbo.setIp(dados[1]);
                                        break;
                                    case "porto":
                                        dbo.setPorto(dados[1]);
                                        break;
                                    case "schema":
                                        dbo.setSchema(dados[1]);
                                        break;
                                    case "dbname":
                                        dbo.setNomeBaseDados(dados[1]);
                                        break;
                                    case "user":
                                        dbo.setUtilizador(dados[1]);
                                        break;
                                    case "password":
                                        dbo.setPassword(dados[1]);
                                        break;
                                    case "driverName":
                                        dbo.setBaseDadosLigacao(dados[1]);
                                        break;
                                }
                            }
                        }
                    }
                } finally {
                    scanner.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dbo;
    }

    private static boolean checkFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
