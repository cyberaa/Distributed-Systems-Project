/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientWeb;

import clients.rmi.ClientInterface;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IdeiaBean;
import objectos.Accao;
import objectos.Ideia;
import objectos.PedidoCompra;
import objectos.PropostaCompra;
import objectos.Tema;
import objectos.TemaIdeia;
import objectos.Transaccao;
import objectos.User;
import objectos.operacoes.CreateIdeia;
import objectos.operacoes.CreateTopic;
import objectos.operacoes.Notification;
import servers.rmi.RMI_Interface;
import suporte.Values;

public final class Client extends UnicastRemoteObject implements ClientInterface {

    private boolean rmiIsUp = false;
    private boolean logged = false;
    private boolean flag = true;
    private RMI_Interface serverInterface = null;
    private User user;
    private static List<CreateTopic> bufferTopicos = new ArrayList<>();
    private static List<CreateIdeia> bufferIdeias = new ArrayList<>();
    private String error_messages = null;
    private String cache = null;
    private boolean firstTime = true;
    private static List<CreateIdeia> propostas = new ArrayList<>();

    public Client() throws RemoteException {
        super();
    }

//    public boolean connect() {
//
//        long inicio = System.currentTimeMillis();
//        boolean result = false;
//        while (flag) {
//            String serv = getServer();
//            String rmiregistry = null;
//            String port = null;
//            if (serv != null && serv.length() > 0) {
//                String dados[] = serv.split(" ");
//                rmiregistry = dados[1].split(":")[0];
//                port = dados[1].split(":")[1];
//            }
//            if (isFirstTime()) {
//                System.out.println("rmiregistry: " + rmiregistry);
//                System.out.println("port: " + port);
//                setFirstTime(false);
//            }
//            String serviceRemote = "serveroperations";
//            try {
//                serverInterface = (RMI_Interface) Naming.lookup("rmi://" + rmiregistry + ":" + port + "/" + serviceRemote + "");
//                if (serverInterface != null) {
//                    setRmiIsUp(true);
//                }
//                result = true;
//                flag = false;
//
//            } catch (Exception e) {
//                //System.out.println("Exception in main: " + e);
//                return false;
//            }
//            if (System.currentTimeMillis() - inicio > 10000) {
//                flag = false;
//                System.err.println("!!!Não existe nenhum servidor disponível, por favor tente mais tarde!!!");
//                //System.exit(0);
//            }
//        }
//        return result;
//        /**
//         * *****************************************************************************************************
//         */
//    }
    public boolean connect() {
        try {
            serverInterface = (RMI_Interface) Naming.lookup("rmi://localhost:7500/dbs");
            String a = serverInterface.sayHello();
            rmiIsUp = true;
            System.out.println(a);
            System.out.println("Server Reached...");
            return true;
        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    /**
     * @return the rmiIsUp
     */
    public boolean isRmiIsUp() {
        return rmiIsUp;
    }

    /**
     * @param aRmiIsUp the rmiIsUp to set
     */
    public void setRmiIsUp(boolean aRmiIsUp) {
        rmiIsUp = aRmiIsUp;
    }

    /**
     * @return the logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param aLogged the logged to set
     */
    public void setLogged(boolean aLogged) {
        logged = aLogged;
    }

    public RMI_Interface getServerInterface() {
        return serverInterface;
    }

    public void setServerInterface(RMI_Interface serverInterface) {
        this.serverInterface = serverInterface;
    }

    public String getError_messages() {
        return error_messages;
    }

    public void setError_messages(String error_messages) {
        this.error_messages = error_messages;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public boolean doRegister() {
        return false;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean doLogin() {
        try {
            user = serverInterface.login_user(user);
            if (user.isOnline()) {
                serverInterface.operationJoin(user, this);
                return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean doRegisto() {
        try {
            serverInterface.regist_user(user);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public ArrayList<String> doOnlineUsers() {
        ArrayList<String> users = null;

        return users;
    }

    public void doLogout() {
        try {
            serverInterface.logout_user(user);
            serverInterface.removeUserTableUsersOnline(user);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Tema> getAllTopics() {
        List<Tema> listaTopicos = null;
        try {
            listaTopicos = serverInterface.get_all_topicos();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTopicos;
    }

    public List<Tema> getAllMyTopics() {
        List<Tema> listaTopicos = null;
        try {
            listaTopicos = serverInterface.get_my_topicos(user);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTopicos;
    }

    public boolean createTopic(Tema tema) {
        try {
            tema.setIdUtilizador(user.getIdUtilizador());
            if (serverInterface.create_topico(tema)) {
                String texto = "O user " + user.getUsername() + " criou um novo topico com o titulo \"" + tema.getNome() + "\"";
                Notification notification = new Notification(texto);
                serverInterface.notifyAllUsers(notification, user);
                return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteTopic(String titulo) {
        try {
            if (serverInterface.delete_topico(user, titulo)) {
                String texto = "O user " + user.getUsername() + " apagou o topico com o titulo \"" + titulo + "\"";
                Notification notification = new Notification(texto);
                serverInterface.notifyAllUsers(notification, user);
                return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean createIdeia(IdeiaBean ideiaBean) {
        Tema tema;
        try {
            tema = serverInterface.get_topico(ideiaBean.getTituloTopico());
            if (tema != null) {
                Ideia ideia = ideiaBean.toIdeia();
                ideia.setId_utilizador(user.getIdUtilizador());
                ideia = serverInterface.create_ideia(ideia);
                System.out.println(ideia.getNome());
                if (ideia.getId_ideia() > 0) {
                    TemaIdeia temaIdeia = new TemaIdeia();
                    temaIdeia.setIdIdeia(ideia.getId_ideia());
                    temaIdeia.setIdTopico(tema.getIdTema());
                    if (serverInterface.insert_topico_ideia(temaIdeia)) {
                        double valorAccao = fazCalculo(Double.parseDouble(ideiaBean.getMontante()));
                        Accao accao = new Accao();
                        accao.setIdIdeia(ideia.getId_ideia());
                        accao.setIdUtilizador(user.getIdUtilizador());
                        accao.setNumAccoes(ideia.getVenda_automatica());
                        accao.setValorTotal(valorAccao);
                        accao.setTipoCompra(Values.AUTOMATICO);
                        if (serverInterface.insert_accao(accao)) {
                            accao = new Accao();
                            accao.setIdIdeia(ideia.getId_ideia());
                            accao.setIdUtilizador(user.getIdUtilizador());
                            accao.setNumAccoes((100000 - ideia.getVenda_automatica()));
                            accao.setValorTotal(valorAccao);
                            accao.setTipoCompra(Values.NAOAUTOMATICO);
                            if (serverInterface.insert_accao(accao)) {
                                serverInterface.update_deicoins(user, Double.parseDouble(ideiaBean.getMontante()));

                                String texto = "O user " + user.getUsername() + " adicinou a ideia com o titulo \"" + ideia.getNome() + "\" ao topico \"" + ideia.getNomeTopico() + "\"";
                                Notification notification = new Notification(texto);
                                serverInterface.notifyAllUsers(notification, user);
                                return true;
                            } else {
                                serverInterface.delete_accao(accao);
                                serverInterface.delete_topico_ideia(temaIdeia);
                                serverInterface.delete_ideia(ideia);
                                return false;
                            }
                        } else {
                            serverInterface.delete_topico_ideia(temaIdeia);
                            serverInterface.delete_ideia(ideia);
                            return false;
                        }
                    } else {
                        serverInterface.delete_ideia(ideia);
                        return false;
                    }
                } else {
                    serverInterface.delete_ideia(ideia);
                    return false;
                }
            } else {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public String buySharesIdea(int referencia, int percentagemCompra) throws RemoteException, SQLException {

        synchronized (serverInterface) {
            Ideia ideia = serverInterface.get_ideia(referencia);
            if (ideia != null) {
                User userProprietarioIdeia = serverInterface.get_utilizador_by_username(ideia.getUsername());
                Accao accao = serverInterface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
                    return "Nao existe accoes automaticas para a ideia com a referencia " + referencia;
//                    if (!sendMensagem(buySharesIdea)) {
//                        serverInterface.adicionaMensagemNoBuffer(user, buySharesIdea);
//                    }
                } else {
                    double saldoUser = user.getDeicoins();
                    double valorTotalDaCompra = percentagemCompra * accao.getValorTotal();
                    if (saldoUser < valorTotalDaCompra) {
//                        buySharesIdea.setSucesso(false);
//                        buySharesIdea.setMensagem("Saldo insuficiente");
                        return "Saldo Insuficiente";
//                        if (!sendMensagem(buySharesIdea)) {
//                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
//                        }
                    } else {
                        if (accao.getNumAccoes() >= percentagemCompra) {
                            if (serverInterface.update_numero_accoes(accao, percentagemCompra, Values.DEBITO)) {
                                accao = serverInterface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                                if (accao.getNumAccoes() == 0) {
                                    serverInterface.update_estado_accao(accao, Values.VENDIDA);
                                }
                                // faz debito na conta do user que quer comprar;
                                if (serverInterface.update_deicoins(user, valorTotalDaCompra)) {
                                    // faz credito na conta do proprietario da ideia
                                    if (serverInterface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {

                                        Accao newAccao = new Accao();
                                        newAccao.setNumAccoes(percentagemCompra);
                                        newAccao.setIdUtilizador(user.getIdUtilizador());
                                        newAccao.setTipoCompra(Values.NAOAUTOMATICO);
                                        newAccao.setValorTotal(accao.getValorTotal());
                                        newAccao.setIdIdeia(ideia.getId_ideia());
                                        if (serverInterface.insert_accao(newAccao)) {
                                            // inserir a transaccao
                                            Transaccao transaccao = new Transaccao();
                                            transaccao.setIdIdeia(ideia.getId_ideia());
                                            transaccao.setIdComprador(user.getIdUtilizador());
                                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
                                            transaccao.setNumAccoes(percentagemCompra);
                                            transaccao.setTipoCompra(Values.AUTOMATICO);
                                            transaccao.setValorTotal(valorTotalDaCompra);

                                            if (serverInterface.insert_transacao(transaccao)) {
//                                                buySharesIdea.setSucesso(true);
//                                                buySharesIdea.setMensagem("Compra realizada com sucesso");
//                                                if (!sendMensagem(buySharesIdea)) {
//                                                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                                                }
                                                String texto = "Foram compradas accoes da ideia com o titulo \"" + ideia.getNome() + "\" ao topico \"" + ideia.getNomeTopico() + "\"";
                                                Notification notification = new Notification(texto);
                                                serverInterface.notifyAllUsers(notification, user);
                                                return "Accoes compradas com sucesso";
                                            } else {
                                                serverInterface.delete_accao(newAccao);
                                                serverInterface.update_deicoins(userProprietarioIdeia, valorTotalDaCompra);
                                                serverInterface.update_deicoins_credito(user, valorTotalDaCompra);
                                                serverInterface.update_estado_accao(accao, Values.ACTIVO);
                                                serverInterface.update_numero_accoes(accao, percentagemCompra, Values.CREDITO);
                                                return "Nao foi possivel actualizar o numero de accoes";
//                                                buySharesIdea.setSucesso(false);
//                                                buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                                                if (!sendMensagem(buySharesIdea)) {
//                                                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                                                }
                                            }

                                        } else {
                                            serverInterface.update_deicoins(userProprietarioIdeia, valorTotalDaCompra);
                                            serverInterface.update_deicoins_credito(user, valorTotalDaCompra);
                                            serverInterface.update_estado_accao(accao, Values.ACTIVO);
                                            serverInterface.update_numero_accoes(accao, percentagemCompra, Values.CREDITO);
                                            return "Nao foi possivel actualizar o numero de accoes";
//                                            buySharesIdea.setSucesso(false);
//                                            buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                                            if (!sendMensagem(buySharesIdea)) {
//                                                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                                            }
                                        }
                                    } else {
                                        serverInterface.update_deicoins_credito(user, valorTotalDaCompra);
                                        serverInterface.update_estado_accao(accao, Values.ACTIVO);
                                        serverInterface.update_numero_accoes(accao, percentagemCompra, Values.CREDITO);
                                        return "Nao foi possivel actualizar o numero de accoes";
//                                        buySharesIdea.setSucesso(false);
//                                        buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                                        if (!sendMensagem(buySharesIdea)) {
//                                            TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                                        }
                                    }
                                } else {
                                    serverInterface.update_estado_accao(accao, Values.ACTIVO);
                                    serverInterface.update_numero_accoes(accao, percentagemCompra, Values.CREDITO);
                                    return "Nao foi possivel actualizar o numero de accoes";
//                                    buySharesIdea.setSucesso(false);
//                                    buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                                    if (!sendMensagem(buySharesIdea)) {
//                                        TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                                    }
                                }

                            } else {
                                return "Nao foi possivel actualizar o numero de accoes";
//                                buySharesIdea.setSucesso(false);
//                                buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                                if (!sendMensagem(buySharesIdea)) {
//                                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                                }
                            }

                        } else {
                            return "O numero de accoes disponivel para venda é inferior ao que pretende comprar";
//                            buySharesIdea.setSucesso(false);
//                            buySharesIdea.setMensagem("O numero de accoes disponivel para venda é inferior ao que pretende comprar.\r\nNumero accoes disponivel = " + accao.getNumAccoes() + "\r\nNumero accoes pretendida = " + buySharesIdea.getNumeroAccoes());
//                            if (!sendMensagem(buySharesIdea)) {
//                                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                            }
                        }
                    }
                }

            } else {
                return "Nao existe nenhuma ideia com a referencia";
//                buySharesIdea.setSucesso(false);
//                buySharesIdea.setMensagem("Nao existe nenhuma ideia com a referencia " + buySharesIdea.getReferenciaDaIdeia());
//                if (!sendMensagem(buySharesIdea)) {
//                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), buySharesIdea);
//                }
            }

        }

    }

    public String MakeOfferBuyShares(int ref, int quant, double valor) throws RemoteException, SQLException {
//        User userDaCompra = makeOfferBuyShares.getUser();
//        userDaCompra = TCPSERVER.existeUserOnTableUsersOnline(userDaCompra);
        synchronized (serverInterface) {
            Ideia ideia = serverInterface.get_ideia(ref);
            if (ideia != null) {
                User userProprietarioIdeia = serverInterface.get_utilizador_by_username(ideia.getUsername());
                Accao accao = serverInterface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                if (accao.getEstado().equals(Values.VENDIDA)) {
                    return "Nao existe accoes para a ideia com a referencia ";
//                    makeOfferBuyShares.setSucesso(false);
//                    makeOfferBuyShares.setMensagem("Nao existe accoes para a ideia com a referencia " + makeOfferBuyShares.getReferenciaIdeia());
//                    if (!sendMensagem(makeOfferBuyShares)) {
//                        TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), makeOfferBuyShares);
//                    }
                } else {
                    double saldoUser = user.getDeicoins();
                    double valorTotalDaCompra = quant * valor;
                    if (saldoUser < valorTotalDaCompra) {
                        return "Saldo insuficiente";
//                        makeOfferBuyShares.setSucesso(false);
//                        makeOfferBuyShares.setMensagem("Saldo insuficiente");
//                        if (!sendMensagem(makeOfferBuyShares)) {
//                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), makeOfferBuyShares);
//                        }
                    } else {
                        PropostaCompra pc = new PropostaCompra();
                        pc.setIdComprador(user.getIdUtilizador());
                        pc.setIdProprietario(userProprietarioIdeia.getIdUtilizador());
                        pc.setNumAccoes(quant);
                        pc.setReferenciaIdeia(ref);
                        pc.setValorPorAccao(valor);
                        serverInterface.adicionaPropostaCompra(ref, pc);
                        return "Proposta aceite com sucesso";
//                        makeOfferBuyShares.setSucesso(true);
//                        makeOfferBuyShares.setMensagem("Proposta aceite");
//                        if (!sendMensagem(makeOfferBuyShares)) {
//                            TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), makeOfferBuyShares);
//                        }
                    }
                }
            } else {
                return "Nao existe nenhuma ideia com a referencia ";
//                makeOfferBuyShares.setSucesso(false);
//                makeOfferBuyShares.setMensagem("Nao existe nenhuma ideia com a referencia " + makeOfferBuyShares.getReferenciaIdeia());
//                if (!sendMensagem(makeOfferBuyShares)) {
//                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), makeOfferBuyShares);
//                }
            }
        }
    }

    public List<Accao> listaPedidosIdeia(int referencia) throws RemoteException, SQLException {

        List<Accao> listaAccao = serverInterface.get_todas_accoes_ideia_estado(referencia, Values.ACTIVO);
        if(listaAccao!=null){
            return listaAccao;
        }

        return null;
    }

//    private void processaOrderToSellShare(int referencia) throws RemoteException, SQLException {
//
//        synchronized (serverInterface) {
//            PedidoCompra pedidoCompra = serverInterface.getPedidosCompra(referencia);
//            if (pedidoCompra != null) {
//                Ideia ideia = serverInterface.get_ideia(referencia);
//                User userProprietarioIdeia = serverInterface.get_utilizador_by_username(user.getNome());
//                List<Accao> listaAccao = serverInterface.get_todas_accoes_ideia_estado(referencia, Values.ACTIVO);
//                if (listaAccao != null && !listaAccao.isEmpty()) {
//                    //verifica se essa ideia so tem accoes do proprietario
//                    if (listaAccao.size() == 1) {
//                        Accao accao = listaAccao.get(0);
//                        if (accao.getIdUtilizador() == userProprietarioIdeia.getIdUtilizador()) {
//                            // compra as accoes ao proprietario
//                            fazTradingAccoes(ideia, accao, referencia, pedidoCompra.getValorMelhorOferta(), pedidoCompra.getMelhorOferta().getNumAccoes(), user, userProprietarioIdeia, Values.NAOAUTOMATICO);
//
//                            serverInterface.adicionaMensagem(user.getUsername(), referencia);
//
//                            //Notifica o user da melhor proposta de compra
//                            PropostaCompra p =  new PropostaCompra();
//                            p.setIdComprador(user.getIdUtilizador());
//                             p.setIdProprietario(userProprietarioIdeia);
//                             
//                        } else {
//                            // faz o trading das accoes com o novo proprietario
//                            User novoUserProprietario = serverInterface.get_utilizador_by_id_utlizador(accao.getIdUtilizador());
//                            fazTradingAccoes(ideia, accao, referencia, pedidoCompra.getValorMelhorOferta(), pedidoCompra.getMelhorOferta().getNumAccoes(), user, novoUserProprietario, Values.NAOAUTOMATICO);
//
//                            serverInterface.adicionaMensagemNoBuffer(user.getUsername(), referencia);
//
//                        }
//
//                    }
//                } else {
//                    //return "Nao existem accoes da ideia com referencia = " + orderToSellShare.getReferenciaIdeia());
//
//                    serverInterface.adicionaMensagemNoBuffer(user.getUsername(), orderToSellShare);
//
//                }
//            } else {
//                orderToSellShare.setSucesso(false);
//                orderToSellShare.setMensagem("Nao existe nenhum pedido de compra a referencia " + orderToSellShare.getReferenciaIdeia());
//                if (!sendMensagem(orderToSellShare)) {
//                    serverInterface.adicionaMensagemNoBuffer(user.getUsername(), orderToSellShare);
//                }
//            }
//        }
//    }
    public void adicionaProposta(int referencia) {

    }

    public String fazTradingAccoes(Ideia ideia, Accao accao, int referencia, double valorTotalDaCompra, int numAccoes, User userDaCompra, User userProprietarioIdeia, String tipoCompraParaTransacao) throws RemoteException, SQLException {
        double saldoUser = userDaCompra.getDeicoins();
        if (saldoUser < valorTotalDaCompra) {
            return "Saldo insuficiente";
        } else {
            if (accao.getNumAccoes() >= numAccoes) {
                if (serverInterface.update_numero_accoes(accao, numAccoes, Values.DEBITO)) {
                    if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
                        accao = serverInterface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                        tipoCompraParaTransacao = Values.AUTOMATICO;
                    } else {
                        accao = serverInterface.get_Accao_Ideia(ideia.getId_ideia());
                    }
                    if (accao.getNumAccoes() == 0) {
                        serverInterface.update_estado_accao(accao, Values.VENDIDA);
                    }
                    // faz debito na conta do user que quer comprar;
                    if (serverInterface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
                        // faz credito na conta do proprietario da ideia
                        if (serverInterface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {

                            Accao newAccao = new Accao();
                            newAccao.setNumAccoes(numAccoes);
                            newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
                            newAccao.setTipoCompra(Values.NAOAUTOMATICO);
                            newAccao.setValorTotal(accao.getValorTotal());
                            newAccao.setIdIdeia(ideia.getId_ideia());
                            serverInterface.insert_accao(newAccao);
                            // inserir a transaccao
                            Transaccao transaccao = new Transaccao();
                            transaccao.setIdIdeia(ideia.getId_ideia());
                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
                            transaccao.setNumAccoes(numAccoes);
                            transaccao.setTipoCompra(tipoCompraParaTransacao);
                            transaccao.setValorTotal(valorTotalDaCompra);

                            serverInterface.insert_transacao(transaccao);
                            serverInterface.removePedidoCompra(referencia);

                            return "Compra realizada com sucesso";

                        }
                    }

                } else {
                    return ("Nao foi possivel actualizar o numero de accoes");
                }

            } else if (accao.getNumAccoes() < numAccoes) {
                int numAccoesPossiveis = numAccoes - accao.getNumAccoes();
                valorTotalDaCompra = numAccoesPossiveis * accao.getValorTotal();
                if (serverInterface.update_numero_accoes(accao, numAccoesPossiveis, Values.DEBITO)) {
                    if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
                        accao = serverInterface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                        tipoCompraParaTransacao = Values.AUTOMATICO;
                    } else {
                        accao = serverInterface.get_Accao_Ideia(ideia.getId_ideia());
                    }
                    if (accao.getNumAccoes() == 0) {
                        serverInterface.update_estado_accao(accao, Values.VENDIDA);
                    }

                    // faz debito na conta do user que quer comprar;
                    if (serverInterface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
                        // faz credito na conta do proprietario da ideia
                        if (serverInterface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {

                            Accao newAccao = new Accao();
                            newAccao.setNumAccoes(numAccoes);
                            newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
                            newAccao.setTipoCompra(Values.NAOAUTOMATICO);
                            newAccao.setValorTotal(accao.getValorTotal());
                            newAccao.setIdIdeia(ideia.getId_ideia());
                            serverInterface.insert_accao(newAccao);
                            // inserir a transaccao
                            Transaccao transaccao = new Transaccao();
                            transaccao.setIdIdeia(ideia.getId_ideia());
                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
                            transaccao.setNumAccoes(numAccoes);
                            transaccao.setTipoCompra(tipoCompraParaTransacao);
                            transaccao.setValorTotal(valorTotalDaCompra);

                            serverInterface.insert_transacao(transaccao);

                            return ("Compra/venda realizada com sucesso");

                        }
                    }
                } else {
                    return ("Nao foi possivel actualizar o numero de accoes");
                }
            }
        }
        return null;

    }

    public String deleteIdeia(int refIdeia) {
        try {
            Ideia ideia = serverInterface.get_ideia(refIdeia);
            if (ideia != null) {
                if (ideia.getId_utilizador() == user.getIdUtilizador()) {
                    if (!serverInterface.existe_outros_proprietarios_ideia(ideia.getId_ideia())) {
                        if (serverInterface.delete_topico_ideia_id_ideia(ideia.getId_ideia())) {
                            if (serverInterface.delete_ideia(ideia)) {

                                String texto = "O user " + user.getUsername() + " apagou a ideia com o titulo \"" + ideia.getNome() + "\" ao topico \"" + ideia.getNomeTopico() + "\"";
                                Notification notification = new Notification(texto);
                                serverInterface.notifyAllUsers(notification, user);

                                return "Ideia removida com sucesso";
                            } else {
                                return "Nao foi possivel apagar a ideia com a referencia " + refIdeia;
                            }
                        } else {
                            return "Nao foi possivel apagar a ideia com a referencia " + refIdeia;
                        }

                    } else {
                        return "Existem outroe proprietarios da ideia com a referencia " + refIdeia;
                    }
                } else {
                    return "Nao e proprietario da ideia com a referencia " + refIdeia;
                }
            } else {
                return "Nao existe nenhuma ideia com a referencia " + refIdeia;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private double fazCalculo(double valorInvestido) {
        return valorInvestido / 100000.00;
    }

    public Object getAllIdeiaTopic(String tituloTopico) {
        Tema tema;
        try {
            tema = serverInterface.get_topico(tituloTopico);
            if (tema != null) {
                List<Ideia> listaIdeias = serverInterface.get_all_ideias_topico(tema.getIdTema());
                return listaIdeias;
            } else {
                return "O tópico que introduziu não existe";
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public List<Transaccao> getMyHistoric() {

        List<Transaccao> listaTransaccao = null;
        try {
            listaTransaccao = serverInterface.get_todas_transaccoes_utilizador(user.getIdUtilizador());
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTransaccao;

    }

    private String getServer() {
        try {
            DatagramSocket aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            String str = "DISCOVER_MASTER_SERVER";

            DatagramPacket request = new DatagramPacket(str.getBytes(), str.getBytes().length, aHost, serverPort);
            aSocket.send(request);

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.setSoTimeout(10000);
            aSocket.receive(reply);
            String resposta = new String(reply.getData()).trim();
            //System.out.println("Recebeu: " + resposta);
            return resposta;

        } catch (SocketException e) {

            System.out.println("Socket: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void callback(Object o) throws RemoteException {

    }

    @Override
    public boolean isClientUp() throws RemoteException {
        return true;
    }

    public static boolean checkFile(String nomeFicheiro) {
        File f = new File(nomeFicheiro);
        if (!f.exists()) {
            try {
                if (f.createNewFile()) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                //e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean saveBuffer(int tipo, User user) {
        boolean ret = false;
        String nomeFicheiro = null;
        if (tipo == 1) {
            nomeFicheiro = user.getUsername() + "_topicos.dat";
        } else if (tipo == 2) {
            nomeFicheiro = user.getUsername() + "_ideias.dat";
        }
        if (checkFile(nomeFicheiro)) {
            ObjectOutputStream oo = null;
            try {
                oo = new ObjectOutputStream(new FileOutputStream(nomeFicheiro));
                if (tipo == 1) {
                    oo.writeObject(bufferTopicos);
                } else if (tipo == 2) {
                    oo.writeObject(bufferIdeias);
                }
                oo.close();
                ret = true;
            } catch (FileNotFoundException e) {
                ret = false;

            } catch (WriteAbortedException e) {
                ret = false;

            } catch (IOException e) {
                ret = false;

            }
        }
        return ret;
    }

    public static void loadBuffer(int tipo, User user) {
        String nomeFicheiro = null;
        if (tipo == 1) {
            nomeFicheiro = user.getUsername() + "_topicos.dat";
        } else if (tipo == 2) {
            nomeFicheiro = user.getUsername() + "_ideias.dat";
        }
        if (checkFile(nomeFicheiro)) {
            FileInputStream fis = null;
            ObjectInputStream oi = null;
            try {

                fis = new FileInputStream(nomeFicheiro);
                oi = new ObjectInputStream(fis);
                if (tipo == 1) {
                    List<CreateTopic> lista = (List<CreateTopic>) oi.readObject();;
                    if (lista != null) {
                        for (CreateTopic createTopic : lista) {
                            bufferTopicos.add(createTopic);
                        }
                    }

                } else if (tipo == 2) {
                    List<CreateIdeia> lista = (List<CreateIdeia>) oi.readObject();;
                    if (lista != null) {
                        for (CreateIdeia createIdeia : lista) {
                            bufferIdeias.add(createIdeia);
                        }
                    }
                }

            } catch (WriteAbortedException e) {
            } catch (EOFException e) {
            } catch (FileNotFoundException e) {
            } catch (ClassNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (oi != null) {
                    try {
                        oi.close();
                    } catch (IOException ex) {
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                    }
                }

            }
        }

    }
}
