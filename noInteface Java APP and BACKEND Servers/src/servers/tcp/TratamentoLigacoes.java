/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servers.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
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
import objectos.operacoes.AllIdeiaTopic;
import objectos.operacoes.AllMyIdeia;
import objectos.operacoes.AllMyTopic;
import objectos.operacoes.AllTopic;
import objectos.operacoes.BuySharesIdea;
import objectos.operacoes.CreateIdeia;
import objectos.operacoes.CreateTopic;
import objectos.operacoes.DeleteIdeia;
import objectos.operacoes.DeleteTopic;
import objectos.operacoes.Login;
import objectos.operacoes.Logout;
import objectos.operacoes.MakeOfferBuyShares;
import objectos.operacoes.MyActiveShares;
import objectos.operacoes.Notification;
import objectos.operacoes.OrderToSellShare;
import objectos.operacoes.Registo;
import objectos.operacoes.SharesSellingPrice;
import objectos.operacoes.ShowTransactionHistory;
import objectos.operacoes.TransactionalTrading;
import servers.rmi.RMI_Interface;
import suporte.Values;

/**
 *
 * @author Iris
 */
public class TratamentoLigacoes extends Thread {

    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    int thread_number;
    final RMI_Interface rMI_Interface;
    
    public TratamentoLigacoes(Socket aClientSocket, int numero, RMI_Interface rMI_Interface) {
        thread_number = numero;
        this.rMI_Interface = rMI_Interface;

        try {
            clientSocket = aClientSocket;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }
    //=============================

    @Override
    public void run() {

        System.out.println("-------------------------");
        System.out.println("Trying to Reach RMI DBS Server...");
        try {
//            rMI_Interface = (RMI_Interface) Naming.lookup("rmi://localhost:7500/dbs");
//            String a = rMI_Interface.sayHello();
//            System.out.println(a);
//            System.out.println("Server Reached...");

            /*Funcionalidades*/
            //an echo server
            while (true) {
                /*menu login */

                Object obj = in.readObject();
                if (obj instanceof Login) {
                    processaLogin((Login) obj);

                } else if (obj instanceof Registo) {
                    processaRegisto((Registo) obj);

                } else if (obj instanceof Logout) {
                    processaLogout((Logout) obj);

                } else if (obj instanceof CreateTopic) {
                    processaTopico((CreateTopic) obj);

                } else if (obj instanceof AllTopic) {
                    processaAllTopic((AllTopic) obj);

                } else if (obj instanceof AllMyTopic) {
                    processaAllMyTopic((AllMyTopic) obj);

                } else if (obj instanceof DeleteTopic) {
                    processaDeleteTopic((DeleteTopic) obj);

                } else if (obj instanceof CreateIdeia) {
                    processaCreateIdeia((CreateIdeia) obj);

                } else if (obj instanceof AllIdeiaTopic) {
                    processaAllIdeiaTopic((AllIdeiaTopic) obj);

                } else if (obj instanceof AllMyIdeia) {
                    processaAllMyIdeia((AllMyIdeia) obj);

                } else if (obj instanceof BuySharesIdea) {
                    processaBuySharesIdea((BuySharesIdea) obj);

                } else if (obj instanceof DeleteIdeia) {
                    processaBuyDeleteIdeia((DeleteIdeia) obj);

                } else if (obj instanceof OrderToSellShare) {
                    processaOrderToSellShare((OrderToSellShare) obj);

                } else if (obj instanceof MakeOfferBuyShares) {
                    processaMakeOfferBuyShares((MakeOfferBuyShares) obj);

                } else if (obj instanceof MyActiveShares) {
                    processaMyActiveShares((MyActiveShares) obj);

                } else if (obj instanceof SharesSellingPrice) {
                    processaSharesSellingPrice((SharesSellingPrice) obj);

                } else if (obj instanceof ShowTransactionHistory) {
                    processaShowTransactionHistory((ShowTransactionHistory) obj);

                }

            }

        } catch (Exception e) {
            //e.printStackTrace();
        }

        System.out.println("-------------------------");

    }

    private void processaLogin(Login login) throws RemoteException, SQLException {
        if (login.isLogged()) {
            User user = rMI_Interface.login_user(login.getUser());
            if (sendMensagem(Values.LOGIN_RECONNECT)) {
                if (!user.isOnline()) {
                    user.setOnline(true);
                }
                TCPSERVER.addUserOnTableUsersOnline(user, out);
            }
        } else {
            User user = rMI_Interface.login_user(login.getUser());
            if (user.isOnline()) {
                if (sendMensagem(Values.LOGIN_OK)) {
                    //rMI_Interface.operationJoin(user, login.getUser().getClientInterfaceImpl());
                    //rMI_Interface.addUserTableUsersOnline(user);
                    TCPSERVER.addUserOnTableUsersOnline(user, out);
                }
            } else {
                sendMensagem(Values.LOGIN_NOK);
            }
        }

    }

    private void processaRegisto(Registo registo) throws RemoteException, IOException {
        if (rMI_Interface.regist_user(registo.getUser())) {
            sendMensagem(Values.REGISTO_OK);
        } else {
            sendMensagem(Values.REGISTO_NOK);
        }
    }

    private void processaLogout(Logout logout) throws RemoteException, IOException, SQLException {
        User user = logout.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        if (user != null) {
            //Vai fazer o logout na base de dados com o id do utilizdor
            if (rMI_Interface.logout_user(user)) {
                rMI_Interface.removeUserTableUsersOnline(user);
                TCPSERVER.removeUserOnTableUsersOnline(user);
            }
        }
    }

    private void processaTopico(CreateTopic createTopic) throws RemoteException, SQLException {
        //User user = createTopic.getUser();
        User user = TCPSERVER.existeUserOnTableUsersOnline(createTopic.getUser());
        if (user != null) {
            Tema tema = createTopic.getTema();
            tema.setIdUtilizador(user.getIdUtilizador());
            if (rMI_Interface.create_topico(tema)) {
                if (!sendMensagem(Values.TOPICO_OK)) {
                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.TOPICO_OK);
                }
                String texto = "O user " + user.getUsername() + " criou um novo topico com o titulo \"" + tema.getNome() + "\"";
                Notification notification = new Notification(texto);
                rMI_Interface.notifyAllUsers(notification, user);

            } else {
                if (!sendMensagem(Values.TOPICO_NOK)) {
                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.TOPICO_NOK);
                }
            }
        } else {
            if (!sendMensagem(Values.TOPICO_NOK)) {
                TCPSERVER.adicionaMensagemNoBuffer(createTopic.getUser().getUsername(), Values.TOPICO_NOK);
            }
        }
    }

    private void processaAllTopic(AllTopic allTopic) throws RemoteException, SQLException {
        List<Tema> listaTopicos = rMI_Interface.get_all_topicos();
        if (listaTopicos != null) {
            allTopic.setListaTemas(listaTopicos);
            sendMensagem(allTopic);
        }
    }

    private void processaAllMyTopic(AllMyTopic allMyTopic) throws RemoteException, SQLException {
        User user = allMyTopic.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        List<Tema> listaTopicos = rMI_Interface.get_my_topicos(user);
        if (listaTopicos != null) {
            allMyTopic.setListaTemas(listaTopicos);
            sendMensagem(allMyTopic);
        }
    }

    private void processaDeleteTopic(DeleteTopic deleteTopic) throws RemoteException, SQLException {
        User user = deleteTopic.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        if (rMI_Interface.delete_topico(user, deleteTopic.getNomeTopico())) {
            if (!sendMensagem(Values.TOPICO_DELETE_OK)) {
                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.TOPICO_DELETE_OK);
            }
            String texto = "O user " + user.getUsername() + " apagou o topico com o titulo \"" + deleteTopic.getNomeTopico() + "\"";
            Notification notification = new Notification(texto);
            rMI_Interface.notifyAllUsers(notification, user);
        } else {
            if (!sendMensagem(Values.TOPICO_DELETE_NOK)) {
                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.TOPICO_DELETE_NOK);
            }
        }
    }
//rever para a meta de BD

    private void processaCreateIdeia(CreateIdeia createIdeia) throws RemoteException, SQLException {
        User user = createIdeia.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        String nomeTopico = createIdeia.getNomeTopico();
        //Tema tema = rMI_Interface.get_topico(user.getIdUtilizador(), nomeTopico);
        Tema tema = rMI_Interface.get_topico(nomeTopico);
        if (tema != null) {
            Ideia ideia = createIdeia.getIdeia();
            ideia.setId_utilizador(user.getIdUtilizador());
            ideia = rMI_Interface.create_ideia(ideia);
            if (ideia.getId_ideia() > 0) {
                TemaIdeia temaIdeia = new TemaIdeia();
                temaIdeia.setIdIdeia(ideia.getId_ideia());
                temaIdeia.setIdTopico(tema.getIdTema());
                if (rMI_Interface.insert_topico_ideia(temaIdeia)) {
                    double valorAccao = fazCalculo(createIdeia.getValorInvestido());
                    Accao accao = new Accao();
                    accao.setIdIdeia(ideia.getId_ideia());
                    accao.setIdUtilizador(user.getIdUtilizador());
                    accao.setNumAccoes(ideia.getVenda_automatica());
                    accao.setValorTotal(valorAccao);
                    accao.setTipoCompra(Values.AUTOMATICO);
                    if (rMI_Interface.insert_accao(accao)) {
                        accao = new Accao();
                        accao.setIdIdeia(ideia.getId_ideia());
                        accao.setIdUtilizador(user.getIdUtilizador());
                        accao.setNumAccoes((100000 - ideia.getVenda_automatica()));
                        accao.setValorTotal(valorAccao);
                        accao.setTipoCompra(Values.NAOAUTOMATICO);
                        if (rMI_Interface.insert_accao(accao)) {
                            rMI_Interface.update_deicoins(user, createIdeia.getValorInvestido());
                            if (!sendMensagem(Values.IDEIA_OK)) {
                                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.IDEIA_OK);
                            }
                            
                            String texto = "O user " + user.getUsername() + " adicinou a ideia com o titulo \"" + ideia.getNome() + "\" ao topico \"" + ideia.getNomeTopico() + "\"";
                            Notification notification = new Notification(texto);
                            rMI_Interface.notifyAllUsers(notification, user);
                        } else {
                            rMI_Interface.delete_accao(accao);
                            rMI_Interface.delete_topico_ideia(temaIdeia);
                            rMI_Interface.delete_ideia(ideia);
                            if (!sendMensagem(Values.IDEIA_NOK)) {
                                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.IDEIA_NOK);
                            }
                        }
                    } else {
                        rMI_Interface.delete_topico_ideia(temaIdeia);
                        rMI_Interface.delete_ideia(ideia);
                        if (!sendMensagem(Values.IDEIA_NOK)) {
                            TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.IDEIA_NOK);
                        }
                    }
                } else {
                    rMI_Interface.delete_ideia(ideia);
                    if (!sendMensagem(Values.IDEIA_NOK)) {
                        TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.IDEIA_NOK);
                    }
                }
            } else {
                rMI_Interface.delete_ideia(ideia);
                if (!sendMensagem(Values.IDEIA_NOK)) {
                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.IDEIA_NOK);
                }
            }
        } else {
            if (!sendMensagem(Values.IDEIA_NOK)) {
                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), Values.IDEIA_NOK);
            }
        }
    }

    private void processaAllIdeiaTopic(AllIdeiaTopic allIdeiaTopic) throws RemoteException, SQLException {
        Tema tema = rMI_Interface.get_topico(allIdeiaTopic.getNomeTopico());
        if (tema != null) {
            List<Ideia> listaIdeias = rMI_Interface.get_all_ideias_topico(tema.getIdTema());
            if (listaIdeias != null) {
                allIdeiaTopic.setListaIdeias(listaIdeias);
                sendMensagem(allIdeiaTopic);
            }
        }
    }

    private void processaAllMyIdeia(AllMyIdeia allMyIdeia) throws RemoteException, SQLException {
        User user = allMyIdeia.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        List<Ideia> listaIdeias = rMI_Interface.get_all_my_ideias(user.getIdUtilizador());
        if (listaIdeias != null) {
            allMyIdeia.setListaIdeias(listaIdeias);
            sendMensagem(allMyIdeia);
        }
    }

    // rever essa funcao para a meta de BD
    private void processaBuySharesIdea(BuySharesIdea buySharesIdea) throws RemoteException, SQLException {
        User userDaCompra = buySharesIdea.getUser();
        userDaCompra = TCPSERVER.existeUserOnTableUsersOnline(userDaCompra);

        synchronized (rMI_Interface) {
            Ideia ideia = rMI_Interface.get_ideia(buySharesIdea.getReferenciaDaIdeia());
            if (ideia != null) {
                User userProprietarioIdeia = rMI_Interface.get_utilizador_by_username(ideia.getUsername());
                Accao accao = rMI_Interface.get_Accao_Automatico_Ideia2(ideia.getId_ideia());
                if (accao==null){
                    buySharesIdea.setSucesso(false);
                    buySharesIdea.setMensagem("Nao existe accoes automaticas para a ideia com a referencia " + buySharesIdea.getReferenciaDaIdeia());
                    if (!sendMensagem(buySharesIdea)) {
                        TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                    }
                } else {
                    double saldoUser = userDaCompra.getDeicoins();
                    double valorTotalDaCompra = buySharesIdea.getNumeroAccoes() * accao.getValorTotal();
                    if (saldoUser < valorTotalDaCompra) {
                        buySharesIdea.setSucesso(false);
                        buySharesIdea.setMensagem("Saldo insuficiente");
                        if (!sendMensagem(buySharesIdea)) {
                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                        }
                    } else {
                        if (accao.getNumAccoes() >= buySharesIdea.getNumeroAccoes()) {
                            if (rMI_Interface.update_numero_accoes(accao, buySharesIdea.getNumeroAccoes(), Values.DEBITO)) {
                                accao = rMI_Interface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                                if (accao.getNumAccoes() == 0) {
                                    rMI_Interface.update_estado_accao(accao, Values.VENDIDA);
                                }
                                // faz debito na conta do user que quer comprar;
                                if (rMI_Interface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
                                    // faz credito na conta do proprietario da ideia
                                    if (rMI_Interface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {

                                        Accao newAccao = new Accao();
                                        newAccao.setNumAccoes(buySharesIdea.getNumeroAccoes());
                                        newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
                                        newAccao.setTipoCompra(Values.NAOAUTOMATICO);
                                        newAccao.setValorTotal(accao.getValorTotal());
                                        newAccao.setIdIdeia(ideia.getId_ideia());
                                        if (rMI_Interface.insert_accao(newAccao)) {
                                            // inserir a transaccao
                                            Transaccao transaccao = new Transaccao();
                                            transaccao.setIdIdeia(ideia.getId_ideia());
                                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
                                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
                                            transaccao.setNumAccoes(buySharesIdea.getNumeroAccoes());
                                            transaccao.setTipoCompra(Values.AUTOMATICO);
                                            transaccao.setValorTotal(valorTotalDaCompra);

                                            if (rMI_Interface.insert_transacao(transaccao)) {
                                                buySharesIdea.setSucesso(true);
                                                buySharesIdea.setMensagem("Compra realizada com sucesso");
                                                if (!sendMensagem(buySharesIdea)) {
                                                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                                                }
                                                String texto = "Foram compradas accoes da ideia com o titulo \"" + ideia.getNome() + "\" ao topico \"" + ideia.getNomeTopico() + "\"";
                                                Notification notification = new Notification(texto);
                                                rMI_Interface.notifyAllUsers(notification, userDaCompra);
                                            } else {
                                                rMI_Interface.delete_accao(newAccao);
                                                rMI_Interface.update_deicoins(userProprietarioIdeia, valorTotalDaCompra);
                                                rMI_Interface.update_deicoins_credito(userDaCompra, valorTotalDaCompra);
                                                rMI_Interface.update_estado_accao(accao, Values.ACTIVO);
                                                rMI_Interface.update_numero_accoes(accao, buySharesIdea.getNumeroAccoes(), Values.CREDITO);

                                                buySharesIdea.setSucesso(false);
                                                buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
                                                if (!sendMensagem(buySharesIdea)) {
                                                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                                                }
                                            }

                                        } else {
                                            rMI_Interface.update_deicoins(userProprietarioIdeia, valorTotalDaCompra);
                                            rMI_Interface.update_deicoins_credito(userDaCompra, valorTotalDaCompra);
                                            rMI_Interface.update_estado_accao(accao, Values.ACTIVO);
                                            rMI_Interface.update_numero_accoes(accao, buySharesIdea.getNumeroAccoes(), Values.CREDITO);

                                            buySharesIdea.setSucesso(false);
                                            buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
                                            if (!sendMensagem(buySharesIdea)) {
                                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                                            }
                                        }
                                    } else {
                                        rMI_Interface.update_deicoins_credito(userDaCompra, valorTotalDaCompra);
                                        rMI_Interface.update_estado_accao(accao, Values.ACTIVO);
                                        rMI_Interface.update_numero_accoes(accao, buySharesIdea.getNumeroAccoes(), Values.CREDITO);

                                        buySharesIdea.setSucesso(false);
                                        buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
                                        if (!sendMensagem(buySharesIdea)) {
                                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                                        }
                                    }
                                } else {
                                    rMI_Interface.update_estado_accao(accao, Values.ACTIVO);
                                    rMI_Interface.update_numero_accoes(accao, buySharesIdea.getNumeroAccoes(), Values.CREDITO);

                                    buySharesIdea.setSucesso(false);
                                    buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
                                    if (!sendMensagem(buySharesIdea)) {
                                        TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                                    }
                                }

                            } else {
                                buySharesIdea.setSucesso(false);
                                buySharesIdea.setMensagem("Nao foi possivel actualizar o numero de accoes");
                                if (!sendMensagem(buySharesIdea)) {
                                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                                }
                            }

                        } else {
                            buySharesIdea.setSucesso(false);
                            buySharesIdea.setMensagem("O numero de accoes disponivel para venda é inferior ao que pretende comprar.\r\nNumero accoes disponivel = " + accao.getNumAccoes() + "\r\nNumero accoes pretendida = " + buySharesIdea.getNumeroAccoes());
                            if (!sendMensagem(buySharesIdea)) {
                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                            }
                        }
                    }
                }

            } else {
                buySharesIdea.setSucesso(false);
                buySharesIdea.setMensagem("Nao existe nenhuma ideia com a referencia " + buySharesIdea.getReferenciaDaIdeia());
                if (!sendMensagem(buySharesIdea)) {
                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), buySharesIdea);
                }
            }

        }
    }

    private void processaBuyDeleteIdeia(DeleteIdeia deleteIdeia) throws RemoteException, SQLException {
        User user = deleteIdeia.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        synchronized (rMI_Interface) {
            Ideia ideia = rMI_Interface.get_ideia(deleteIdeia.getReferenciaIdeia());
            if (ideia != null) {
                if (ideia.getId_utilizador() == user.getIdUtilizador()) {
                    if (!rMI_Interface.existe_outros_proprietarios_ideia(ideia.getId_ideia())) {
                        if (rMI_Interface.delete_topico_ideia_id_ideia(ideia.getId_ideia())) {
                            if (rMI_Interface.delete_ideia(ideia)) {
                                deleteIdeia.setSucesso(true);
                                deleteIdeia.setMensagem("Ideia removida com sucesso");
                                if (!sendMensagem(deleteIdeia)) {
                                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), deleteIdeia);
                                }
                                String texto = "O user " + user.getUsername() + " apagou a ideia com o titulo \"" + ideia.getNome() + "\" ao topico \"" + ideia.getNomeTopico() + "\"";
                                Notification notification = new Notification(texto);
                                rMI_Interface.notifyAllUsers(notification, user);
                            } else {
                                deleteIdeia.setSucesso(false);
                                deleteIdeia.setMensagem("Nao foi possivel apagar a ideia com a referencia " + deleteIdeia.getReferenciaIdeia());
                                if (!sendMensagem(deleteIdeia)) {
                                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), deleteIdeia);
                                }
                            }
                        } else {
                            deleteIdeia.setSucesso(false);
                            deleteIdeia.setMensagem("Nao foi possivel apagar a ideia com a referencia " + deleteIdeia.getReferenciaIdeia());
                            if (!sendMensagem(deleteIdeia)) {
                                TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), deleteIdeia);
                            }
                        }

                    } else {
                        deleteIdeia.setSucesso(false);
                        deleteIdeia.setMensagem("Existem outroe proprietarios da ideia com a referencia " + deleteIdeia.getReferenciaIdeia());
                        if (!sendMensagem(deleteIdeia)) {
                            TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), deleteIdeia);
                        }
                    }
                } else {
                    deleteIdeia.setSucesso(false);
                    deleteIdeia.setMensagem("Nao e proprietario da ideia com a referencia " + deleteIdeia.getReferenciaIdeia());
                    if (!sendMensagem(deleteIdeia)) {
                        TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), deleteIdeia);
                    }
                }
            } else {
                deleteIdeia.setSucesso(false);
                deleteIdeia.setMensagem("Nao existe nenhuma ideia com a referencia " + deleteIdeia.getReferenciaIdeia());
                if (!sendMensagem(deleteIdeia)) {
                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), deleteIdeia);
                }
            }
        }
    }

    private void processaMyActiveShares(MyActiveShares myActiveShares) throws RemoteException, SQLException {
        User user = myActiveShares.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        List<Accao> listaAccoes = rMI_Interface.get_todas_accoes_activa_utilizador(user.getIdUtilizador());
        if (listaAccoes == null) {
            myActiveShares.setSucesso(false);
            myActiveShares.setMensagem("Nao foi possivel obter a lista de accoes activas do utilizador");
            sendMensagem(myActiveShares);
        } else if (listaAccoes.isEmpty()) {
            myActiveShares.setSucesso(false);
            myActiveShares.setMensagem("Nao existem accoes activas para o utilizador");
            sendMensagem(myActiveShares);
        } else {
            myActiveShares.setSucesso(true);
            myActiveShares.setListaAccoes(listaAccoes);
            sendMensagem(myActiveShares);
        }

    }

    private void processaShowTransactionHistory(ShowTransactionHistory showTransactionHistory) throws RemoteException, SQLException {
        User user = showTransactionHistory.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        List<Transaccao> listaTransaccoes = rMI_Interface.get_todas_transaccoes_utilizador(user.getIdUtilizador());
        if (listaTransaccoes == null) {
            showTransactionHistory.setSucesso(false);
            showTransactionHistory.setMensagem("Nao foi possivel obter a lista das transaccoes do utilizador");
            sendMensagem(showTransactionHistory);
        } else if (listaTransaccoes.isEmpty()) {
            showTransactionHistory.setSucesso(false);
            showTransactionHistory.setMensagem("Nao existem transaccoes para o utilizador");
            sendMensagem(showTransactionHistory);
        } else {
            showTransactionHistory.setSucesso(true);
            showTransactionHistory.setListaTransaccoes(listaTransaccoes);
            sendMensagem(showTransactionHistory);
        }
    }

    private void processaSharesSellingPrice(SharesSellingPrice sharesSellingPrice) throws RemoteException, SQLException {
        User user = sharesSellingPrice.getUser();
        user = TCPSERVER.existeUserOnTableUsersOnline(user);
        synchronized (rMI_Interface) {
            Accao accao = rMI_Interface.get_Accao_Ideia(sharesSellingPrice.getReferenciaIdeia());
            if (accao != null) {
                if (rMI_Interface.update_preco_de_accao_activa(user.getIdUtilizador(), sharesSellingPrice.getReferenciaIdeia(), accao.getIdAccao(), sharesSellingPrice.getNovoValor())) {
                    sharesSellingPrice.setSucesso(true);
                    sharesSellingPrice.setMensagem("Preco da accao alterado com sucesso");
                    if (!sendMensagem(sharesSellingPrice)) {
                        TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), sharesSellingPrice);
                    }
                } else {
                    sharesSellingPrice.setSucesso(false);
                    sharesSellingPrice.setMensagem("Nao foi possivel alterar o preco da accao");
                    if (!sendMensagem(sharesSellingPrice)) {
                        TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), sharesSellingPrice);
                    }
                }
            } else {
                sharesSellingPrice.setSucesso(false);
                sharesSellingPrice.setMensagem("Nao foi possivel alterar o preco da accao");
                if (!sendMensagem(sharesSellingPrice)) {
                    TCPSERVER.adicionaMensagemNoBuffer(user.getUsername(), sharesSellingPrice);
                }
            }

        }
    }

    private void processaOrderToSellShare(OrderToSellShare orderToSellShare) throws RemoteException, SQLException {
        User userDaCompra = orderToSellShare.getUser();
        userDaCompra = TCPSERVER.existeUserOnTableUsersOnline(userDaCompra);
        synchronized (rMI_Interface) {
            PedidoCompra pedidoCompra = rMI_Interface.getPedidosCompra(orderToSellShare.getReferenciaIdeia());
            if (pedidoCompra != null) {
                Ideia ideia = rMI_Interface.get_ideia(orderToSellShare.getReferenciaIdeia());
                User userProprietarioIdeia = rMI_Interface.get_utilizador_by_username(orderToSellShare.getUser().getNome());
                List<Accao> listaAccao = rMI_Interface.get_todas_accoes_ideia_estado(orderToSellShare.getReferenciaIdeia(), Values.ACTIVO);
                if (listaAccao != null && !listaAccao.isEmpty()) {
                    //verifica se essa ideia so tem accoes do proprietario
                    if (listaAccao.size() == 1) {
                        Accao accao = listaAccao.get(0);
                        if (accao.getIdUtilizador() == userProprietarioIdeia.getIdUtilizador()) {
                            // compra as accoes ao proprietario
                            fazTradingAccoes(ideia, accao, orderToSellShare, pedidoCompra.getValorMelhorOferta(), pedidoCompra.getMelhorOferta().getNumAccoes(),userDaCompra, userProprietarioIdeia, Values.NAOAUTOMATICO);
                            if (!sendMensagem(orderToSellShare)) {
                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), orderToSellShare);
                            }
                            //Notifica o user da melhor proposta de compra
                        } else {
                            // faz o trading das accoes com o novo proprietario
                            User novoUserProprietario = rMI_Interface.get_utilizador_by_id_utlizador(accao.getIdUtilizador());
                            fazTradingAccoes(ideia, accao, orderToSellShare, pedidoCompra.getValorMelhorOferta(), pedidoCompra.getMelhorOferta().getNumAccoes(),userDaCompra, novoUserProprietario, Values.NAOAUTOMATICO);
                            if (!sendMensagem(orderToSellShare)) {
                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), orderToSellShare);
                            }

                        }

                    } 
                } else {
                    orderToSellShare.setSucesso(false);
                    orderToSellShare.setMensagem("Nao existem accoes da ideia com referencia = " + orderToSellShare.getReferenciaIdeia());
                    if (!sendMensagem(orderToSellShare)) {
                        TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), orderToSellShare);
                    }
                }
            } else {
                orderToSellShare.setSucesso(false);
                orderToSellShare.setMensagem("Nao existe nenhum pedido de compra a referencia " + orderToSellShare.getReferenciaIdeia());
                if (!sendMensagem(orderToSellShare)) {
                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), orderToSellShare);
                }
            }
        }
    }

    private void processaMakeOfferBuyShares(MakeOfferBuyShares makeOfferBuyShares) throws RemoteException, SQLException {
        User userDaCompra = makeOfferBuyShares.getUser();
        userDaCompra = TCPSERVER.existeUserOnTableUsersOnline(userDaCompra);
        synchronized (rMI_Interface) {
            Ideia ideia = rMI_Interface.get_ideia(makeOfferBuyShares.getReferenciaIdeia());
            if (ideia != null) {
                User userProprietarioIdeia = rMI_Interface.get_utilizador_by_username(ideia.getUsername());
                Accao accao = rMI_Interface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                if (accao.getEstado().equals(Values.VENDIDA)) {
                    makeOfferBuyShares.setSucesso(false);
                    makeOfferBuyShares.setMensagem("Nao existe accoes para a ideia com a referencia " + makeOfferBuyShares.getReferenciaIdeia());
                    if (!sendMensagem(makeOfferBuyShares)) {
                        TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), makeOfferBuyShares);
                    }
                } else {
                    double saldoUser = userDaCompra.getDeicoins();
                    double valorTotalDaCompra = makeOfferBuyShares.getNumAccoes() * makeOfferBuyShares.getValorPorAccao();
                    if (saldoUser < valorTotalDaCompra) {
                        makeOfferBuyShares.setSucesso(false);
                        makeOfferBuyShares.setMensagem("Saldo insuficiente");
                        if (!sendMensagem(makeOfferBuyShares)) {
                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), makeOfferBuyShares);
                        }
                    } else {
                        PropostaCompra pc = new PropostaCompra();
                        pc.setIdComprador(userDaCompra.getIdUtilizador());
                        pc.setIdProprietario(userProprietarioIdeia.getIdUtilizador());
                        pc.setNumAccoes(makeOfferBuyShares.getNumAccoes());
                        pc.setReferenciaIdeia(makeOfferBuyShares.getReferenciaIdeia());
                        pc.setValorPorAccao(makeOfferBuyShares.getValorPorAccao());
                        rMI_Interface.adicionaPropostaCompra(makeOfferBuyShares.getReferenciaIdeia(), pc);
                        makeOfferBuyShares.setSucesso(true);
                        makeOfferBuyShares.setMensagem("Proposta aceite");
                        if (!sendMensagem(makeOfferBuyShares)) {
                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), makeOfferBuyShares);
                        }
                    }
                }
            } else {
                makeOfferBuyShares.setSucesso(false);
                makeOfferBuyShares.setMensagem("Nao existe nenhuma ideia com a referencia " + makeOfferBuyShares.getReferenciaIdeia());
                if (!sendMensagem(makeOfferBuyShares)) {
                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), makeOfferBuyShares);
                }
            }
        }
    }

//    private void processaTransactionalTrading(TransactionalTrading transactionalTrading) throws RemoteException, SQLException {
//        User userDaCompra = transactionalTrading.getUser();
//        userDaCompra = TCPSERVER.existeUserOnTableUsersOnline(userDaCompra);
//        synchronized (rMI_Interface) {
//            Ideia ideia = rMI_Interface.get_ideia(transactionalTrading.getReferenciaDaIdeia());
//            if (ideia != null) {
//                User userProprietarioIdeia = rMI_Interface.get_utilizador_by_username(ideia.getUsername());
//                List<Accao> listaAccao = rMI_Interface.get_todas_accoes_ideia_estado(ideia.getId_ideia(), Values.ACTIVO);
//                if (listaAccao != null && !listaAccao.isEmpty()) {
//                    //verifica se essa ideia so tem accoes do proprietario
//                    if (listaAccao.size() == 1) {
//                        Accao accao = listaAccao.get(0);
//                        if (accao.getIdUtilizador() == userProprietarioIdeia.getIdUtilizador()) {
//                            // compra as accoes ao proprietario
//                            fazTradingAccoes(ideia, accao, transactionalTrading, userDaCompra, userProprietarioIdeia, Values.NAOAUTOMATICO);
//                            if (!sendMensagem(transactionalTrading)) {
//                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                            }
//
//                        } else {
//                            // faz o trading das accoes com o novo proprietario
//                            User novoUserProprietario = rMI_Interface.get_utilizador_by_id_utlizador(accao.getIdUtilizador());
//                            fazTradingAccoes(ideia, accao, transactionalTrading, userDaCompra, novoUserProprietario, Values.NAOAUTOMATICO);
//                            if (!sendMensagem(transactionalTrading)) {
//                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                            }
//
//                        }
//                    } else if (listaAccao.size() == 2) {
//                        int count = 0;
//                        for (Accao accao : listaAccao) {
//                            if (accao.getIdUtilizador() == userProprietarioIdeia.getIdUtilizador()) {
//                                count++;
//                            }
//                        }
//                        // significa que não existem outros utilizadores com accoes dessa ideia
//                        if (count == 2) {
//                            // compra as accoes ao proprietario
//                            for (Accao accao : listaAccao) {
//                                if (transactionalTrading.getNumeroAccoes() > 0) {
//                                    fazTradingAccoes(ideia, accao, transactionalTrading, userDaCompra, userProprietarioIdeia, Values.NAOAUTOMATICO);
//                                } else {
//                                    break;
//                                }
//                            }
//                            if (transactionalTrading.getNumeroAccoes() == 0) {
//                                transactionalTrading.setSucesso(true);
//                                transactionalTrading.setMensagem("Trading realizado com sucesso");
//                            } else {
//                                transactionalTrading.setSucesso(true);
//                                transactionalTrading.setMensagem("Nao foi possivel fazer o Trading de todas as accoes que o utilizador queria comprar");
//                            }
//                            if (!sendMensagem(transactionalTrading)) {
//                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                            }
//                        } else {
//                            // faz o trading das accoes com o novo proprietario e o antigo sefor necessario mais accoes para completar o numero requerido pelo comprador
//                            // compra as accoes ao proprietario
//                            for (Accao accao : listaAccao) {
//                                if (transactionalTrading.getNumeroAccoes() > 0) {
//                                    fazTradingAccoes(ideia, accao, transactionalTrading, userDaCompra, userProprietarioIdeia, Values.NAOAUTOMATICO);
//                                } else {
//                                    break;
//                                }
//                            }
//                            if (transactionalTrading.getNumeroAccoes() == 0) {
//                                transactionalTrading.setSucesso(true);
//                                transactionalTrading.setMensagem("Trading realizado com sucesso");
//                            } else {
//                                transactionalTrading.setSucesso(true);
//                                transactionalTrading.setMensagem("Nao foi possivel fazer o Trading de todas as accoes que o utilizador queria comprar");
//                            }
//                            if (!sendMensagem(transactionalTrading)) {
//                                TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                            }
//                        }
//                    } else {
//                        // faz o trading das accoes com o novo proprietario e o antigo sefor necessario mais accoes para completar o numero requerido pelo comprador
//                        // compra as accoes ao proprietario
//                        for (Accao accao : listaAccao) {
//                            if (transactionalTrading.getNumeroAccoes() > 0) {
//                                fazTradingAccoes(ideia, accao, transactionalTrading, userDaCompra, userProprietarioIdeia, Values.NAOAUTOMATICO);
//                            } else {
//                                break;
//                            }
//                        }
//                        if (transactionalTrading.getNumeroAccoes() == 0) {
//                            transactionalTrading.setSucesso(true);
//                            transactionalTrading.setMensagem("Trading realizado com sucesso");
//                        } else {
//                            transactionalTrading.setSucesso(true);
//                            transactionalTrading.setMensagem("Nao foi possivel fazer o Trading de todas as accoes que o utilizador queria comprar");
//                        }
//                        if (!sendMensagem(transactionalTrading)) {
//                            TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                        }
//                    }
//                } else {
//                    transactionalTrading.setSucesso(false);
//                    transactionalTrading.setMensagem("Nao existem accoes da ideia com referencia = " + transactionalTrading.getReferenciaDaIdeia());
//                    if (!sendMensagem(transactionalTrading)) {
//                        TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                    }
//                }
//            } else {
//                transactionalTrading.setSucesso(false);
//                transactionalTrading.setMensagem("Nao existe nenhuma ideia com a referencia " + transactionalTrading.getReferenciaDaIdeia());
//                if (!sendMensagem(transactionalTrading)) {
//                    TCPSERVER.adicionaMensagemNoBuffer(userDaCompra.getUsername(), transactionalTrading);
//                }
//            }
//        }
//    }

    private void fazTradingAccoes(Ideia ideia, Accao accao, OrderToSellShare sellShare, double valorTotalDaCompra, int numAccoes, User userDaCompra, User userProprietarioIdeia, String tipoCompraParaTransacao) throws RemoteException, SQLException {
        double saldoUser = userDaCompra.getDeicoins();
        if (saldoUser < valorTotalDaCompra) {
            sellShare.setSucesso(false);
            sellShare.setMensagem("Saldo insuficiente");
        } else {
            if (accao.getNumAccoes() >= numAccoes) {
                if (rMI_Interface.update_numero_accoes(accao, numAccoes, Values.DEBITO)) {
                    if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
                        accao = rMI_Interface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                        tipoCompraParaTransacao = Values.AUTOMATICO;
                    } else {
                        accao = rMI_Interface.get_Accao_Ideia(ideia.getId_ideia());
                    }
                    if (accao.getNumAccoes() == 0) {
                        rMI_Interface.update_estado_accao(accao, Values.VENDIDA);
                    }
                    // faz debito na conta do user que quer comprar;
                    if (rMI_Interface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
                        // faz credito na conta do proprietario da ideia
                        if (rMI_Interface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {

                            Accao newAccao = new Accao();
                            newAccao.setNumAccoes(numAccoes);
                            newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
                            newAccao.setTipoCompra(Values.NAOAUTOMATICO);
                            newAccao.setValorTotal(accao.getValorTotal());
                            newAccao.setIdIdeia(ideia.getId_ideia());
                            rMI_Interface.insert_accao(newAccao);
                            // inserir a transaccao
                            Transaccao transaccao = new Transaccao();
                            transaccao.setIdIdeia(ideia.getId_ideia());
                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
                            transaccao.setNumAccoes(numAccoes);
                            transaccao.setTipoCompra(tipoCompraParaTransacao);
                            transaccao.setValorTotal(valorTotalDaCompra);

                            rMI_Interface.insert_transacao(transaccao);
                            rMI_Interface.removePedidoCompra(sellShare.getReferenciaIdeia());

                            sellShare.setSucesso(true);
                            sellShare.setMensagem("Compra realizada com sucesso");

                        }
                    }

                } else {
                    sellShare.setSucesso(false);
                    sellShare.setMensagem("Nao foi possivel actualizar o numero de accoes");
                }

            } else if (accao.getNumAccoes() < numAccoes) {
                int numAccoesPossiveis = numAccoes - accao.getNumAccoes();
                valorTotalDaCompra = numAccoesPossiveis * accao.getValorTotal();
                if (rMI_Interface.update_numero_accoes(accao, numAccoesPossiveis, Values.DEBITO)) {
                    if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
                        accao = rMI_Interface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
                        tipoCompraParaTransacao = Values.AUTOMATICO;
                    } else {
                        accao = rMI_Interface.get_Accao_Ideia(ideia.getId_ideia());
                    }
                    if (accao.getNumAccoes() == 0) {
                        rMI_Interface.update_estado_accao(accao, Values.VENDIDA);
                    }

                    // faz debito na conta do user que quer comprar;
                    if (rMI_Interface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
                        // faz credito na conta do proprietario da ideia
                        if (rMI_Interface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {

                            Accao newAccao = new Accao();
                            newAccao.setNumAccoes(numAccoes);
                            newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
                            newAccao.setTipoCompra(Values.NAOAUTOMATICO);
                            newAccao.setValorTotal(accao.getValorTotal());
                            newAccao.setIdIdeia(ideia.getId_ideia());
                            rMI_Interface.insert_accao(newAccao);
                            // inserir a transaccao
                            Transaccao transaccao = new Transaccao();
                            transaccao.setIdIdeia(ideia.getId_ideia());
                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
                            transaccao.setNumAccoes(numAccoes);
                            transaccao.setTipoCompra(tipoCompraParaTransacao);
                            transaccao.setValorTotal(valorTotalDaCompra);

                            rMI_Interface.insert_transacao(transaccao);

                            sellShare.setSucesso(true);
                            sellShare.setMensagem("Compra/venda realizada com sucesso");
                            
                        }
                    }
                } else {
                    sellShare.setSucesso(false);
                    sellShare.setMensagem("Nao foi possivel actualizar o numero de accoes");
                }
            }
        }

    }

//    private void fazTradingAccoes(Ideia ideia, Accao accao, TransactionalTrading transactionalTrading, User userDaCompra, User userProprietarioIdeia, String tipoCompraParaTransacao) throws RemoteException, SQLException {
//        double saldoUser = userDaCompra.getDeicoins();
//        double valorTotalDaCompra = transactionalTrading.getNumeroAccoes() * accao.getValorTotal();
//        if (saldoUser < valorTotalDaCompra) {
//            transactionalTrading.setSucesso(false);
//            transactionalTrading.setMensagem("Saldo insuficiente");
//        } else {
//            if (accao.getNumAccoes() >= transactionalTrading.getNumeroAccoes()) {
//                if (rMI_Interface.update_numero_accoes(accao, transactionalTrading.getNumeroAccoes(), Values.DEBITO)) {
//                    if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
//                        accao = rMI_Interface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
//                        tipoCompraParaTransacao = Values.AUTOMATICO;
//                    } else {
//                        accao = rMI_Interface.get_Accao_Ideia(ideia.getId_ideia());
//                    }
//                    if (accao.getNumAccoes() == 0) {
//                        rMI_Interface.update_estado_accao(accao, Values.VENDIDA);
//                    }
//                    // faz debito na conta do user que quer comprar;
//                    if (rMI_Interface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
//                        // faz credito na conta do proprietario da ideia
//                        if (rMI_Interface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {
//
//                            Accao newAccao = new Accao();
//                            newAccao.setNumAccoes(transactionalTrading.getNumeroAccoes());
//                            newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
//                            newAccao.setTipoCompra(Values.NAOAUTOMATICO);
//                            newAccao.setValorTotal(accao.getValorTotal());
//                            newAccao.setIdIdeia(ideia.getId_ideia());
//                            rMI_Interface.insert_accao(newAccao);
//                            // inserir a transaccao
//                            Transaccao transaccao = new Transaccao();
//                            transaccao.setIdIdeia(ideia.getId_ideia());
//                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
//                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
//                            transaccao.setNumAccoes(transactionalTrading.getNumeroAccoes());
//                            transaccao.setTipoCompra(tipoCompraParaTransacao);
//                            transaccao.setValorTotal(valorTotalDaCompra);
//
//                            rMI_Interface.insert_transacao(transaccao);
//
//                            transactionalTrading.setNumeroAccoes(0);
//                            transactionalTrading.setSucesso(true);
//                            transactionalTrading.setMensagem("Compra realizada com sucesso");
//
//                        }
//                    }
//
//                } else {
//                    transactionalTrading.setSucesso(false);
//                    transactionalTrading.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                }
//
//            } else if (accao.getNumAccoes() < transactionalTrading.getNumeroAccoes()) {
//                int numAccoesPossiveis = transactionalTrading.getNumeroAccoes() - accao.getNumAccoes();
//                valorTotalDaCompra = numAccoesPossiveis * accao.getValorTotal();
//                if (rMI_Interface.update_numero_accoes(accao, numAccoesPossiveis, Values.DEBITO)) {
//                    if (accao.getTipoCompra().equals(Values.AUTOMATICO)) {
//                        accao = rMI_Interface.get_Accao_Automatico_Ideia(ideia.getId_ideia());
//                        tipoCompraParaTransacao = Values.AUTOMATICO;
//                    } else {
//                        accao = rMI_Interface.get_Accao_Ideia(ideia.getId_ideia());
//                    }
//                    if (accao.getNumAccoes() == 0) {
//                        rMI_Interface.update_estado_accao(accao, Values.VENDIDA);
//                    }
//
//                    // faz debito na conta do user que quer comprar;
//                    if (rMI_Interface.update_deicoins(userDaCompra, valorTotalDaCompra)) {
//                        // faz credito na conta do proprietario da ideia
//                        if (rMI_Interface.update_deicoins_credito(userProprietarioIdeia, valorTotalDaCompra)) {
//
//                            Accao newAccao = new Accao();
//                            newAccao.setNumAccoes(transactionalTrading.getNumeroAccoes());
//                            newAccao.setIdUtilizador(userDaCompra.getIdUtilizador());
//                            newAccao.setTipoCompra(Values.NAOAUTOMATICO);
//                            newAccao.setValorTotal(accao.getValorTotal());
//                            newAccao.setIdIdeia(ideia.getId_ideia());
//                            rMI_Interface.insert_accao(newAccao);
//                            // inserir a transaccao
//                            Transaccao transaccao = new Transaccao();
//                            transaccao.setIdIdeia(ideia.getId_ideia());
//                            transaccao.setIdComprador(userDaCompra.getIdUtilizador());
//                            transaccao.setIdVendedor(userProprietarioIdeia.getIdUtilizador());
//                            transaccao.setNumAccoes(transactionalTrading.getNumeroAccoes());
//                            transaccao.setTipoCompra(tipoCompraParaTransacao);
//                            transaccao.setValorTotal(valorTotalDaCompra);
//
//                            rMI_Interface.insert_transacao(transaccao);
//
//                            transactionalTrading.setSucesso(true);
//                            transactionalTrading.setMensagem("Compra realizada com sucesso");
//                            transactionalTrading.setNumeroAccoes(transactionalTrading.getNumeroAccoes() - numAccoesPossiveis);
//                        }
//                    }
//                } else {
//                    transactionalTrading.setSucesso(false);
//                    transactionalTrading.setMensagem("Nao foi possivel actualizar o numero de accoes");
//                }
//            }
//        }
//
//    }

    private double fazCalculo(double valorInvestido) {
        return valorInvestido / 100000.00;
    }

    private boolean sendMensagem(Object obj) {
        try {
            out.writeObject(obj);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
