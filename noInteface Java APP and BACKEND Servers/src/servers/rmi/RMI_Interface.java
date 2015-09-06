package servers.rmi;

import clients.rmi.ClientInterface;
import java.io.IOException;
import java.rmi.*;
import java.sql.SQLException;
import java.util.List;
import objectos.Accao;
import objectos.Ideia;
import objectos.PedidoCompra;
import objectos.PropostaCompra;
import objectos.Tema;
import objectos.TemaIdeia;
import objectos.Transaccao;
import objectos.User;
import objectos.operacoes.Notification;

public interface RMI_Interface extends Remote {

    public Object operationJoin(User user,ClientInterface c)throws RemoteException;
    
    public void notifyAllUsers(Notification notification, User user) throws RemoteException;
    
    //public void isUp()throws RemoteException;
    
//    public boolean addUserTableUsersOnline(User user)throws RemoteException;
    
    public boolean removeUserTableUsersOnline(User user)throws RemoteException;
    
    public String sayHello() throws java.rmi.RemoteException;
    
    //public Session getSessions() throws java.rmi.RemoteException;
    
    public User login_user(User user) throws java.rmi.RemoteException,SQLException;

    public boolean regist_user(User user) throws java.rmi.RemoteException, IOException;
    
    public boolean logout_user(User user) throws java.rmi.RemoteException, SQLException;
    
    public boolean create_topico(Tema tema) throws java.rmi.RemoteException, SQLException;
    
    public List<Tema> get_all_topicos() throws java.rmi.RemoteException, SQLException;
    
    public List<Tema> get_my_topicos(User user) throws java.rmi.RemoteException, SQLException;
    
    public List<Ideia> get_all_ideias_topico(int idTopico) throws RemoteException, SQLException;
    
    public List<Ideia> get_all_my_ideias(int idUtilizador) throws RemoteException, SQLException;
    
    public Tema get_topico(int idUtilizador, String nomeTopico) throws java.rmi.RemoteException, SQLException;
    
    public Tema get_topico(String nomeTopico) throws java.rmi.RemoteException, SQLException;
    
    public Tema get_topico(int idTopico) throws java.rmi.RemoteException, SQLException;
    
    public boolean delete_topico(User user, String nomeTopico) throws java.rmi.RemoteException, SQLException;
    
    public Ideia create_ideia(Ideia ideia) throws java.rmi.RemoteException, SQLException;
    
    public boolean delete_ideia(Ideia ideia) throws java.rmi.RemoteException, SQLException;

    public boolean insert_topico_ideia(TemaIdeia temaIdeia) throws java.rmi.RemoteException, SQLException;
    
    public boolean delete_topico_ideia(TemaIdeia temaIdeia) throws java.rmi.RemoteException, SQLException;
    
    public boolean delete_topico_ideia_id_ideia(int idIdeia) throws java.rmi.RemoteException, SQLException;
    
    public boolean update_deicoins(User user, double valorInvestido) throws java.rmi.RemoteException, SQLException;
    
    public boolean update_deicoins_credito(User user, double valorInvestido) throws java.rmi.RemoteException, SQLException;
    
    public boolean insert_accao(Accao accao) throws java.rmi.RemoteException, SQLException;
    
    public boolean delete_accao(Accao accao) throws java.rmi.RemoteException, SQLException;
    
    public User get_utilizador_by_username(String username) throws java.rmi.RemoteException, SQLException;
    
    public User get_utilizador_by_id_utlizador(int idUtilizador) throws java.rmi.RemoteException, SQLException;
    
    public List<User> get_todos_utilizadores() throws java.rmi.RemoteException, SQLException;
    
    public Ideia get_ideia(int idIdeia) throws java.rmi.RemoteException, SQLException;
    
    public Accao get_Accao_Automatico_Ideia(int idIdeia) throws RemoteException, SQLException;
     public Accao get_Accao_Automatico_Ideia2(int idIdeia) throws RemoteException, SQLException;
    
    public Accao get_Accao_Ideia(int idIdeia) throws RemoteException, SQLException;
    
    public boolean update_numero_accoes(Accao accao, int numAccao, String tipo) throws java.rmi.RemoteException, SQLException;
    
    public boolean update_estado_accao(Accao accao, String estado) throws java.rmi.RemoteException, SQLException;
    
    public boolean insert_transacao(Transaccao transaccao) throws java.rmi.RemoteException, SQLException;
    
    public boolean existe_outros_proprietarios_ideia(int idIdeia) throws java.rmi.RemoteException, SQLException;
    
    public List<Accao> get_todas_accoes_ideia_estado(int idIdeia, String estado) throws java.rmi.RemoteException, SQLException;
    
    public List<Accao> get_todas_accoes_activa_utilizador(int idUtilizador) throws java.rmi.RemoteException, SQLException;
    
    public boolean update_preco_de_accao_activa(int idUtilizador, int idIdeia, int idAccao, double novoValor) throws java.rmi.RemoteException, SQLException;
          
    public List<Transaccao> get_todas_transaccoes_utilizador(int idUtilizador) throws java.rmi.RemoteException, SQLException;
    
    public boolean adicionaPropostaCompra(int referenciaIdeia, PropostaCompra pc) throws java.rmi.RemoteException, SQLException;
    
    public boolean removePedidoCompra(int referenciaIdeia) throws java.rmi.RemoteException;
    
    public PedidoCompra getPedidosCompra(int referenciaIdeia)  throws java.rmi.RemoteException;
}



