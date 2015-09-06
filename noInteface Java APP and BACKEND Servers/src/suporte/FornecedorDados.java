/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suporte;

import conn_bd.DBObject;
import conn_bd.Database;
import file.LeituraFicheiro;
import interfacesImpl.InterfaceAccaoImpl;
import interfacesImpl.InterfaceFavoritosImpl;
import interfacesImpl.InterfaceIdeiaImpl;
import interfacesImpl.InterfaceSeguidoresImpl;
import interfacesImpl.InterfaceTopicoIdeiaImpl;
import interfacesImpl.InterfaceTopicoImpl;
import interfacesImpl.InterfaceTransaccaoImpl;
import interfacesImpl.InterfaceUtilizadorImpl;

/**
 *
 * @author Iris
 */
public class FornecedorDados {
    private static FornecedorDados instancia;
    private Database database;
    private InterfaceAccaoImpl interfaceAccaoImpl;
    private InterfaceFavoritosImpl interfaceFavoritosImpl;
    private InterfaceIdeiaImpl interfaceIdeiaImpl;
    private InterfaceSeguidoresImpl interfaceSeguidoresImpl;
    private InterfaceTopicoIdeiaImpl interfaceTopicoIdeiaImpl;
    private InterfaceTopicoImpl interfaceTopicoImpl;
    private InterfaceTransaccaoImpl interfaceTransaccaoImpl;
    private InterfaceUtilizadorImpl interfaceUtilizadorImpl;
    
    private FornecedorDados(){
        DBObject dbo = LeituraFicheiro.getParametrosConfiguracaoBD("conf.properties");
        database = Database.getDBConnection(dbo);
        
        interfaceAccaoImpl = new InterfaceAccaoImpl(database);
        interfaceFavoritosImpl = new InterfaceFavoritosImpl(database);
        interfaceIdeiaImpl = new InterfaceIdeiaImpl(database);
        interfaceSeguidoresImpl = new InterfaceSeguidoresImpl(database);
        interfaceTopicoIdeiaImpl = new InterfaceTopicoIdeiaImpl(database);
        interfaceTopicoImpl = new InterfaceTopicoImpl(database);
        interfaceTransaccaoImpl = new InterfaceTransaccaoImpl(database);
        interfaceUtilizadorImpl = new InterfaceUtilizadorImpl(database);
    }
    
    private static FornecedorDados getFornecedorDados(){
        if(instancia == null){
            try{
                instancia = new FornecedorDados();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return instancia;
    }

    public static InterfaceAccaoImpl getInterfaceAccaoImpl() {
        return getFornecedorDados().interfaceAccaoImpl;
    }

    public static InterfaceFavoritosImpl getInterfaceFavoritosImpl() {
        return getFornecedorDados().interfaceFavoritosImpl;
    }

    public static InterfaceIdeiaImpl getInterfaceIdeiaImpl() {
        return getFornecedorDados().interfaceIdeiaImpl;
    }

    public static InterfaceSeguidoresImpl getInterfaceSeguidoresImpl() {
        return getFornecedorDados().interfaceSeguidoresImpl;
    }

    public static InterfaceTopicoIdeiaImpl getInterfaceTopicoIdeiaImpl() {
        return getFornecedorDados().interfaceTopicoIdeiaImpl;
    }

    public static InterfaceTopicoImpl getInterfaceTopicoImpl() {
        return getFornecedorDados().interfaceTopicoImpl;
    }

    public static InterfaceTransaccaoImpl getInterfaceTransaccaoImpl() {
        return getFornecedorDados().interfaceTransaccaoImpl;
    }

    public static InterfaceUtilizadorImpl getInterfaceUtilizadorImpl() {
        return getFornecedorDados().interfaceUtilizadorImpl;
    }
    
    
}
