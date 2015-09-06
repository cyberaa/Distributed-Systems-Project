
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servers.rmi;

import clients.rmi.ClientInterface;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objectos.Accao;
import objectos.Ideia;
import objectos.PedidoCompra;
import objectos.PropostaCompra;
import objectos.Session;
import objectos.Tema;
import objectos.TemaIdeia;
import objectos.Transaccao;
import objectos.User;
import objectos.operacoes.Notification;
import suporte.FornecedorDados;
import suporte.Values;

/**
 *
 * @author Iris
 */
public class RMI_ServerImpl extends UnicastRemoteObject implements RMI_Interface {

    private Session session;
    private ThreadRMIClientConnection threadRMIClientConnection;
    private ThreadLimpaBuffer threadLimpaBuffer;
    public RMI_ServerImpl() throws RemoteException {
        super();

    }

    public void initThreadRMIClientConnection() {
        threadRMIClientConnection = new ThreadRMIClientConnection(this);
        threadRMIClientConnection.setDaemon(true);
        threadRMIClientConnection.start();
    }

    public void initThreadLimpaBuffer() {
        threadLimpaBuffer = new ThreadLimpaBuffer(this);
        threadLimpaBuffer.setDaemon(true);
        threadLimpaBuffer.start();
    }
    
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ThreadRMIClientConnection getThreadRMIClientConnection() {
        return threadRMIClientConnection;
    }

    public void setThreadRMIClientConnection(ThreadRMIClientConnection threadRMIClientConnection) {
        this.threadRMIClientConnection = threadRMIClientConnection;
    }

    private synchronized void notifyAll(Notification notification, User user) {
        try {
            List<User> listaUsers = get_todos_utilizadores();
            if (listaUsers != null) {
                for (User u : listaUsers) {
                    User userTemp = session.existeUserOnTableUsersOnline(u);
                    if (userTemp != null && !userTemp.getUsername().equals(user.getUsername())) {
                        ClientInterface out = session.getClientInterfaceUserOnTableUsersOnline(userTemp);
                        if (out != null) {
                            try {
                                out.callback(notification); // envia notificacao para o user online
                            } catch (Exception ex) {
                                adicionaMensagemNoBuffer(user.getUsername(), notification);
                            }

                        }
                    } else if (userTemp == null) { // coloca no buffer a notificacao para enviar ao user que esta offline
                        adicionaMensagemNoBuffer(u.getUsername(), notification);
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RMI_ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RMI_ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public synchronized static void adicionaMensagemNoBuffer(String username, Notification msg) {
        if (RMI_Server.notificacoes.containsKey(username)) {
            RMI_Server.notificacoes.get(username).add(msg);
        } else {
            List<Notification> lista = new ArrayList<>();
            lista.add(msg);
            RMI_Server.notificacoes.put(username, lista);
        }
    }

    public synchronized void esvaziarBuffer() {
        if (!RMI_Server.notificacoes.isEmpty()) {
            List<String> listaKeysParaRemoverDoBuffer = new ArrayList<>();

            if (!RMI_Server.notificacoes.isEmpty()) {
                for (Map.Entry pairs : RMI_Server.notificacoes.entrySet()) {
                    String username = (String) pairs.getKey();
                    List<Notification> listaMsg = (List<Notification>) pairs.getValue();
                    User user = session.existeUserOnTableUsersOnline(username);
                    if (user != null) {
                        ClientInterface out = session.getClientInterfaceUserOnTableUsersOnline(user);
                        if (out != null) {
                            int countIndex = 0;
                            for (int index = 0; index < listaMsg.size(); index++) {
                                Notification msg = listaMsg.get(index);
                                try {
                                    out.callback(msg);
                                    countIndex++;
                                } catch (RemoteException ex) {
                                    //Logger.getLogger(RMI_ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                            if (countIndex == listaMsg.size()) {
                                listaKeysParaRemoverDoBuffer.add(username);
                            }
                        }
                    }
                }
            }
            if (!listaKeysParaRemoverDoBuffer.isEmpty()) {
                for (String string : listaKeysParaRemoverDoBuffer) {
                    RMI_Server.notificacoes.remove(string);
                }
            }
        }
    }

    public synchronized void updateTabelaUsersOnline() {
        HashMap<User, ClientInterface> clients = session.getUsersOnline();
        if (clients != null && !clients.isEmpty()) {
            for (Map.Entry pairs : clients.entrySet()) {
                User user = (User) pairs.getKey();
                ClientInterface clientInterface = (ClientInterface) pairs.getValue();
                if (clientInterface != null) {
                    try {
                        clientInterface.isClientUp();
                    } catch (RemoteException ex) {
                        session.removeUserOnTableUsersOnline(user);
                        break;
                    }
                }
            }
        }
    }

    public synchronized void adicionaPedidoCompra(PropostaCompra propostaCompra, int referenciaIdeia){
        if(!RMI_Server.pedidosCompras.containsKey((Integer)referenciaIdeia)){
            PedidoCompra pedidoCompra = new PedidoCompra();
            pedidoCompra.getListaPropostasCompras().add(propostaCompra);
            RMI_Server.pedidosCompras.put((Integer)referenciaIdeia,pedidoCompra);
        }else{
            RMI_Server.pedidosCompras.get((Integer)referenciaIdeia).getListaPropostasCompras().add(propostaCompra);
            updateDadosPedidoCompra(referenciaIdeia);
        }
    }
    public synchronized boolean removerPedidoCompra(int referenciaIdeia){
        if(RMI_Server.pedidosCompras.containsKey((Integer)referenciaIdeia)){
            RMI_Server.pedidosCompras.remove((Integer)referenciaIdeia);
            return true;
        }
        return false;
    }
    public PedidoCompra getPedidoCompra(int referenciaIdeia){
        if(RMI_Server.pedidosCompras.containsKey((Integer)referenciaIdeia)){
            return RMI_Server.pedidosCompras.get((Integer)referenciaIdeia);
        }
        return null;
    }
    private void updateDadosPedidoCompra(int referenciaIdeia){
        if(RMI_Server.pedidosCompras.containsKey((Integer)referenciaIdeia)){
            List<PropostaCompra> listaPropostaCompras = RMI_Server.pedidosCompras.get((Integer)referenciaIdeia).getListaPropostasCompras();
            double maxProposta = 0.0;
            int id = 0;
            for (PropostaCompra propostaCompra : listaPropostaCompras) {
                if(propostaCompra.getValorPorAccao() > maxProposta){
                    maxProposta = propostaCompra.getValorPorAccao();
                    id = propostaCompra.getIdComprador();
                    RMI_Server.pedidosCompras.get((Integer)referenciaIdeia).setMelhorOferta(propostaCompra);
                }
            }
            RMI_Server.pedidosCompras.get((Integer)referenciaIdeia).setValorMelhorOferta(maxProposta);
            RMI_Server.pedidosCompras.get((Integer)referenciaIdeia).setIdCompradorMelhorOferta(id);
        }
    }
    @Override
    public User login_user(User user) throws RemoteException, SQLException {
        User u = FornecedorDados.getInterfaceUtilizadorImpl().fazLogin(user);
        if (u != null) {
            if(u.isOnline()){
                u.setOnline(false);
                user.cloneUser(u);
                return user;
            }else{
                if (FornecedorDados.getInterfaceUtilizadorImpl().updateEstado(user.getIdUtilizador(), true)) {
                    u.setOnline(true);
                } else {
                    u.setOnline(false);
                }
            }
            user.cloneUser(u);
        }else{
            user.setOnline(false);
        }
        return user;
    }

    @Override
    public boolean regist_user(User user) throws RemoteException, IOException {
        if (!FornecedorDados.getInterfaceUtilizadorImpl().existeLogin(user.getUsername())) {
            return FornecedorDados.getInterfaceUtilizadorImpl().inserirUtilizador(user);
        } else {
            return false;
        }

    }

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("Connection Test...");
        return "ACK";
    }

//    @Override
//    public Session getSessions() throws RemoteException {
//        return session;
//    }
    @Override
    public boolean logout_user(User user) throws RemoteException {
        if (user != null) {
            if (FornecedorDados.getInterfaceUtilizadorImpl().updateEstado(user.getIdUtilizador(), false)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean create_topico(Tema tema) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().inserirTema(tema);
    }

    @Override
    public List<Tema> get_all_topicos() throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().getTodosOsTemas();
    }

    @Override
    public List<Tema> get_my_topicos(User user) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().getTodosOsTemaDeUmUtilizador(user.getIdUtilizador());
    }

    @Override
    public boolean delete_topico(User user, String nomeTopico) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().apagarUmTemaUtilizadorByIdUtilizadorAndNomeTema(user.getIdUtilizador(), nomeTopico);
    }

    @Override
    public Ideia create_ideia(Ideia ideia) throws RemoteException, SQLException {
        FornecedorDados.getInterfaceIdeiaImpl().inserirIdeia(ideia);
        return ideia;
    }

    @Override
    public boolean delete_ideia(Ideia ideia) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceIdeiaImpl().apagarIdeiaByIdIdeia(ideia.getId_ideia());
    }

    @Override
    public Tema get_topico(int idUtilizador, String nomeTopico) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().getTemaByIdUtilizadorAndNomeTema(idUtilizador, nomeTopico);
    }

    @Override
    public boolean insert_topico_ideia(TemaIdeia temaIdeia) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoIdeiaImpl().inserirTopicoIdeia(temaIdeia);
    }

    @Override
    public Tema get_topico(String nomeTopico) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().getTemaByNomeTema(nomeTopico);
    }

    @Override
    public List<Ideia> get_all_ideias_topico(int idTopico) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoIdeiaImpl().getTodasIdeiasDeUmTopicos(idTopico);
    }

    @Override
    public List<Ideia> get_all_my_ideias(int idUtilizador) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceIdeiaImpl().getTodasAsIdeiasDeUmUtilizadorByIdUtilizador(idUtilizador);
    }

    @Override
    public boolean update_deicoins(User user, double valorInvestido) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceUtilizadorImpl().updateDeicoins(user.getIdUtilizador(), valorInvestido);
    }

    @Override
    public boolean insert_accao(Accao accao) throws RemoteException, SQLException {
        if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
            return FornecedorDados.getInterfaceAccaoImpl().inserirAccaoAutomatica(accao);
        } else {
            return FornecedorDados.getInterfaceAccaoImpl().inserirAccaoNaoAutomatica(accao);
        }
    }

    @Override
    public User get_utilizador_by_username(String username) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceUtilizadorImpl().getUtilizadorByLogin(username);
    }

    @Override
    public User get_utilizador_by_id_utlizador(int idUtilizador) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceUtilizadorImpl().getUtilizadorById(idUtilizador);
    }

    @Override
    public Ideia get_ideia(int idIdeia) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceIdeiaImpl().getUmaIdeia(idIdeia);
    }

    @Override
    public Accao get_Accao_Automatico_Ideia(int idIdeia) throws RemoteException, SQLException {
        List<Accao> listaAccoes = FornecedorDados.getInterfaceAccaoImpl().getTodasAccoesIdeiasEstado(idIdeia, Values.ACTIVO);
        if (listaAccoes.isEmpty()) {
            return null;
        } else {
            return listaAccoes.get(0);
        }
    }

    @Override
    public Accao get_Accao_Ideia(int idIdeia) throws RemoteException, SQLException {
        List<Accao> listaAccoes = FornecedorDados.getInterfaceAccaoImpl().getTodasAccoesIdeiasEstado(idIdeia, Values.NAOAUTOMATICO);
        if (listaAccoes.isEmpty()) {
            return null;
        } else {
            return listaAccoes.get(0);
        }
    }

    @Override
    public boolean update_deicoins_credito(User user, double valorInvestido) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceUtilizadorImpl().updateDeicoinsCredito(user.getIdUtilizador(), valorInvestido);
    }

    @Override
    public boolean update_numero_accoes(Accao accao, int numAccao, String tipo) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceAccaoImpl().updateNumeroAccoes(accao.getIdUtilizador(), accao.getIdIdeia(), accao.getIdAccao(), numAccao, tipo);
    }

    @Override
    public boolean update_estado_accao(Accao accao, String estado) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceAccaoImpl().updateEstado(accao.getIdUtilizador(), accao.getIdIdeia(), accao.getIdAccao(), estado);
    }

    @Override
    public boolean insert_transacao(Transaccao transaccao) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTransaccaoImpl().inserirTransaccao(transaccao);
    }

    @Override
    public boolean existe_outros_proprietarios_ideia(int idIdeia) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTransaccaoImpl().existemOutrosProprietariosDaIdeia(idIdeia);
    }

    @Override
    public List<Accao> get_todas_accoes_ideia_estado(int idIdeia, String estado) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceAccaoImpl().getTodasAccoesIdeiasEstado(idIdeia, estado);
    }

    @Override
    public List<Accao> get_todas_accoes_activa_utilizador(int idUtilizador) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceAccaoImpl().getTodasAccoesUtilizadorEstado(idUtilizador, Values.ACTIVO);
    }

    @Override
    public boolean update_preco_de_accao_activa(int idUtilizador, int idIdeia, int idAccao, double novoValor) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceAccaoImpl().updateValorTotalAccoes(idUtilizador, idIdeia, idAccao, novoValor);
    }

    @Override
    public List<Transaccao> get_todas_transaccoes_utilizador(int idUtilizador) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTransaccaoImpl().getTodasTransaccoesUtilizador(idUtilizador);
    }

    @Override
    public List<User> get_todos_utilizadores() throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceUtilizadorImpl().allUsers();
    }

    @Override
    public boolean delete_topico_ideia(TemaIdeia temaIdeia) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoIdeiaImpl().apagarTemaIdeia(temaIdeia);
    }

    @Override
    public boolean delete_accao(Accao accao) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceAccaoImpl().apagarAccao(accao);
    }

    @Override
    public Tema get_topico(int idTopico) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoImpl().getTemaByIdTema(idTopico);
    }

    @Override
    public boolean delete_topico_ideia_id_ideia(int idIdeia) throws RemoteException, SQLException {
        return FornecedorDados.getInterfaceTopicoIdeiaImpl().apagarTemaIdeia(idIdeia);
    }

    @Override
    public Boolean operationJoin(User user, ClientInterface c) throws RemoteException {
        if (user != null) {
            if(session.existeUserOnTableUsersOnline(user)!=null){
                session.updateClientInterfaceOnTableUsersOnline(user, c);
            }else{
                session.addUserOnTableUsersOnline(user, c);
            }
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void notifyAllUsers(Notification notification, User user) throws RemoteException {
        notifyAll(notification, user);
    }

    @Override
    public boolean removeUserTableUsersOnline(User user) throws RemoteException {
        return this.session.removeUserOnTableUsersOnline(user);
    }

    void setUserOffline(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean adicionaPropostaCompra(int referenciaIdeia, PropostaCompra pc) throws RemoteException {
        adicionaPedidoCompra(pc, referenciaIdeia);
        return true;
    }

    @Override
    public boolean removePedidoCompra(int referenciaIdeia) throws RemoteException {
        return removerPedidoCompra(referenciaIdeia);
    }

    @Override
    public PedidoCompra getPedidosCompra(int referenciaIdeia) throws RemoteException {
        return getPedidoCompra(referenciaIdeia);
    }

    @Override
    public Accao get_Accao_Automatico_Ideia2(int idIdeia) throws RemoteException, SQLException {
        List<Accao> listaAccoes = FornecedorDados.getInterfaceAccaoImpl().getTodasAccoesIdeiasEstado2(idIdeia, Values.ACTIVO);
        if (listaAccoes.isEmpty()) {
            return null;
        } else {
            return listaAccoes.get(0);
        }
    }
}
