/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import objectos.Accao;
import objectos.Ideia;
import objectos.Tema;
import objectos.Transaccao;
import objectos.operacoes.AllIdeiaTopic;
import objectos.operacoes.AllMyIdeia;
import objectos.operacoes.AllMyTopic;
import objectos.operacoes.AllTopic;
import objectos.operacoes.BuySharesIdea;
import objectos.operacoes.DeleteIdeia;
import objectos.operacoes.MakeOfferBuyShares;
import objectos.operacoes.MyActiveShares;
import objectos.operacoes.Notification;
import objectos.operacoes.OrderToSellShare;
import objectos.operacoes.SharesSellingPrice;
import objectos.operacoes.ShowTransactionHistory;
import objectos.operacoes.TransactionalTrading;
import suporte.Values;

/**
 *
 * @author Iris
 */
public class Read extends Thread{
    private ObjectInputStream in;
    private boolean stop;
    private boolean running;
    private TCPClient tCPClient;
    public Read(ObjectInputStream in, TCPClient tCPClient) {
        this.in = in;
        this.stop = false;
        this.running = false;
        this.tCPClient = tCPClient;
    }

    @Override
    public void run() {
        running = true;
        while (!stop) {
            try {
                Object obj =  in.readObject();
                processaInformacao(obj);
            } catch (IOException e) {
                stop = true;
                tCPClient.setFirstTime(false);
                tCPClient.reconnect();
                //e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
               // e.printStackTrace();
            }
            
        }
    }

    private void processaInformacao(Object obj) {
        if(obj instanceof String){
            String msg = (String) obj;
            if(msg.equals(Values.TOPICO_OK)){
                System.out.println("Topico criado com sucesso!!!");
            }else if(msg.equals(Values.TOPICO_NOK)){
                System.err.println("!!!Nao foi possivel cria o topico!!!");
            }else if(msg.equals(Values.TOPICO_DELETE_OK)){
                System.out.println("!!!Topico removido com sucesso!!!");
            }else if(msg.equals(Values.TOPICO_DELETE_NOK)){
                System.err.println("!!!Nao foi possivel remover o topico!!!");
            }else if(msg.equals(Values.IDEIA_NOK)){
                System.err.println("!!!Nao foi possivel submeter a ideia!!!");
            }else if(msg.equals(Values.IDEIA_OK)){
                System.out.println("!!!Ideia submetida com sucesso!!!");
            }else if(msg.equals(Values.LOGIN_RECONNECT)){
                
            }
        }else if(obj instanceof AllTopic){
            AllTopic at = (AllTopic)obj;
            if(at.getListaTemas()!=null && !at.getListaTemas().isEmpty()){
                for (Tema tema : at.getListaTemas()) {
                    System.out.println(tema.toString());
                }
            }else if(at.getListaTemas()!=null && at.getListaTemas().isEmpty()){
                System.err.println("!!!Nao existem topicos disponiveis!!!");
            }else {
                System.err.println("Erro ao carregar os topicos");
            }
        }else if(obj instanceof AllMyTopic){
            AllMyTopic at = (AllMyTopic)obj;
            if(at.getListaTemas()!=null && !at.getListaTemas().isEmpty()){
                for (Tema tema : at.getListaTemas()) {
                    System.out.println(tema.toString());
                }
            }else if(at.getListaTemas()!=null && at.getListaTemas().isEmpty()){
                System.err.println("!!!Nao criou nenhum topico!!!");
            }else {
                System.err.println("!!!Erro ao carregar os topicos!!!");
            }
        }else if(obj instanceof AllIdeiaTopic){
            AllIdeiaTopic at = (AllIdeiaTopic)obj;
            if(at.getListaIdeias()!=null && !at.getListaIdeias().isEmpty()){
                for (Ideia ideia : at.getListaIdeias()) {
                    System.out.println(ideia.toString());
                }
            }else if(at.getListaIdeias()!=null && at.getListaIdeias().isEmpty()){
                System.err.println("!!!O topico "+at.getNomeTopico()+" não tem ideias!!!");
            }else {
                System.err.println("!!!Erro ao carregar as ideias do topico "+at.getNomeTopico()+"!!!");
            }
        }else if(obj instanceof AllMyIdeia){
            AllMyIdeia at = (AllMyIdeia)obj;
            if(at.getListaIdeias()!=null && !at.getListaIdeias().isEmpty()){
                for (Ideia ideia : at.getListaIdeias()) {
                    System.out.println(ideia.toString());
                }
            }else if(at.getListaIdeias()!=null && at.getListaIdeias().isEmpty()){
                System.err.println("!!!Nao tem ideias submetidas!!!");
            }else {
                System.err.println("!!!Erro ao carregar as ideias do utilizador!!!");
            }
        }else if(obj instanceof BuySharesIdea){
            BuySharesIdea at = (BuySharesIdea)obj;
            if(at.isSucesso()){
                System.out.println("!!!"+at.getMensagem()+"!!!");
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof DeleteIdeia){
            DeleteIdeia at = (DeleteIdeia)obj;
            if(at.isSucesso()){
                System.out.println("!!!"+at.getMensagem()+"!!!");
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof TransactionalTrading){
            TransactionalTrading at = (TransactionalTrading)obj;
            if(at.isSucesso()){
                System.out.println("!!!"+at.getMensagem()+"!!!");
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof MyActiveShares){
            MyActiveShares at = (MyActiveShares)obj;
            if(at.isSucesso()){
                for (Accao accao : at.getListaAccoes()) {
                    System.out.println(accao.toString());
                }
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof SharesSellingPrice){
            SharesSellingPrice at = (SharesSellingPrice)obj;
            if(at.isSucesso()){
                System.out.println("!!!"+at.getMensagem()+"!!!");
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof ShowTransactionHistory){
            ShowTransactionHistory at = (ShowTransactionHistory)obj;
            if(at.isSucesso()){
                for (Transaccao transaccao : at.getListaTransaccoes()) {
                    System.out.println(transaccao.toString());
                }
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof Notification){
            Notification at = (Notification)obj;
            System.err.println("!!!"+at.getTexto()+"!!!");
        }else if(obj instanceof MakeOfferBuyShares){
            MakeOfferBuyShares at = (MakeOfferBuyShares)obj;
            if(at.isSucesso()){
                System.out.println("!!!"+at.getMensagem()+"!!!");
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }else if(obj instanceof OrderToSellShare){
            OrderToSellShare at = (OrderToSellShare)obj;
            if(at.isSucesso()){
                System.out.println("!!!"+at.getMensagem()+"!!!");
            }else {
                System.err.println("!!!"+at.getMensagem()+"!!!");
            }
        }
        
        
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
}
