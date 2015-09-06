/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conn_bd;

/**
 *
 * @author Iris
 */
public class DBObject {
    private String ip="";
    private String porto="";
    private String nomeBaseDados="";
    private String schema="";
    private String utilizador="";
    private String password="";
    private String baseDadosLigacao="";

    public DBObject() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorto() {
        return porto;
    }

    public void setPorto(String porto) {
        this.porto = porto;
    }

    public String getNomeBaseDados() {
        return nomeBaseDados;
    }

    public void setNomeBaseDados(String nomeBaseDados) {
        this.nomeBaseDados = nomeBaseDados;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseDadosLigacao() {
        return baseDadosLigacao;
    }

    public void setBaseDadosLigacao(String baseDadosLigacao) {
        this.baseDadosLigacao = baseDadosLigacao;
    }

    @Override
    public String toString() {
        return "DBObject{" + "ip=" + ip + ", porto=" + porto + ", nomeBaseDados=" + nomeBaseDados + ", schema=" + schema + ", utilizador=" + utilizador + ", password=" + password + ", baseDadosLigacao=" + baseDadosLigacao + '}';
    }
    
}
