/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import objectos.Ideia;
import objectos.Tema;
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
import objectos.operacoes.OrderToSellShare;
import objectos.operacoes.Registo;
import objectos.operacoes.SharesSellingPrice;
import objectos.operacoes.ShowTransactionHistory;
import objectos.operacoes.TransactionalTrading;
import suporte.Values;

/**
 *
 * @author Iris
 */
public class OperacoesCliente {

    private Socket s = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner scanner = null;
    private boolean isLogged = false;
    
    public OperacoesCliente(ObjectOutputStream out, ObjectInputStream in, Scanner scanner) {
        this.out = out;
        this.in = in;
        this.scanner = scanner;
    }
    
    public User doLogin() {
        User user = new User();
        try {
            System.out.println("Login System - IDT");
            System.out.println("Username: ");
            String username = scanner.nextLine();
            user.setUsername(username);
            System.out.println("Password: ");
            String pass = scanner.nextLine();
            user.setPassw(pass);
            Login login = new Login(user);
            // envia login
            out.writeObject(login);
            // recebe uma mensagem de sucesso ou insucesso
            Object obj = in.readObject();
            if (obj instanceof String) {
                String msg = (String) obj;
                if (msg.equals(Values.LOGIN_OK)) {
                    user.setOnline(true);
                    Values.ClientMenu(user.getUsername());
                }else{
                    System.err.println("!!!Não foi possivel efectuar o login!!!");
                    Values.menu();
                }
            }
        } catch (ClassNotFoundException | IOException ex) {
            return null;

        }
        return user;

    }
    public User doLogin(User user) {
        try {
            Login login = new Login(user);
            login.setLogged(true);
            // envia login
            out.writeObject(login);
            // recebe uma mensagem de sucesso ou insucesso
            user.setOnline(true);
            
        } catch (IOException ex) {
            return null;

        }
        return user;

    }
    public void doRegisto() {
        User user = new User();
        try {
            System.out.println("Username: ");
            user.setUsername(scanner.nextLine());
            System.out.println("Password: ");
            user.setPassw(scanner.nextLine());
            System.out.println("Nome: ");
            user.setNome(scanner.nextLine());
            System.out.println("Email: ");
            user.setEmail(scanner.nextLine());
            System.out.println("Pais: ");
            user.setPais(scanner.nextLine());
            System.out.println("Idade: ");
            user.setIdade(scanner.nextInt());
            
            Registo registo = new Registo(user);
            out.writeObject(registo);
            Object obj = in.readObject();
            if (obj instanceof String) {
                String msg = (String) obj;
                if (msg.equals(Values.REGISTO_OK)) {
                    System.out.println("!!!Registo efectuado com sucesso!!!");
                }else if (msg.equals(Values.REGISTO_NOK)){
                    System.err.println("!!!Não foi possivel efectuar o registo!!!");
                }
                Values.menu();
            }
        } catch (ClassNotFoundException | IOException ex) {
            

        }

    }
    public boolean doLogout(User user) {
        try {
            Logout logout = new Logout(user);
            out.writeObject(logout);
        } catch (IOException ex) {
        
        }
        return true;

    }
    public CreateTopic createTopic(User user){
        Tema tema = new Tema();
        CreateTopic createTopic = null;
        try {
            System.out.println("Nome do Topico: ");
            tema.setNome(scanner.nextLine());
            System.out.println("HashTag do Topico: ");
            tema.setHashtag(scanner.nextLine());
            createTopic = new CreateTopic(tema, user);
            out.writeObject(createTopic);
            Values.ClientMenu();
        } catch (IOException ex) {
        }
        return createTopic;
    }
    public boolean getAllTopic(){
        try {
            out.writeObject(new AllTopic());
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean getAllMyTopic(User user){
        try {
            out.writeObject(new AllMyTopic(user));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean deleteTopic(User user){
        try {
            
            System.out.println("Nome do Topico: ");
            String nomeTopico = scanner.nextLine();
            out.writeObject(new DeleteTopic(user, nomeTopico));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public CreateIdeia createIdeia(User user){
        Ideia ideia = new Ideia();
        CreateIdeia createIdeia = null;
        try { 
            
            System.out.println("Nome do Topico: ");
            String nomeTopico = scanner.nextLine();
            System.out.println("Nome da Ideia: ");
            ideia.setNome(scanner.nextLine());
            System.out.println("Descricao da Ideia: ");
            ideia.setDescricao(scanner.nextLine());
            System.out.println("Qual o montante que quer investir na sua ideia?");
            double valorInvestido = scanner.nextDouble();
            System.out.println("Introduza a percentagem da ideia que é vendida automaticamente: ");
            ideia.setVenda_automatica(scanner.nextInt());
            createIdeia = new CreateIdeia(user,ideia,nomeTopico,valorInvestido);
            
            out.writeObject(createIdeia);
            Values.ClientMenu();
        } catch (IOException ex) {
        }
        return createIdeia;
    }
    public boolean getAllIdeiaFromTopic(){
        try {
            
            System.out.println("Nome do Topico: ");
            String nomeTopico = scanner.nextLine();
            out.writeObject(new AllIdeiaTopic(nomeTopico));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean getAllMyIdeias(User user){
        try {
            out.writeObject(new AllMyIdeia(user));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean buySharesOfAnIdeia(User user){
        try {
            System.out.println("Introduza a Referencia da Ideia: ");
            int referencia = scanner.nextInt();
            System.out.println("Introduza a quantidade de accoes que deseja comprar: ");
            int numAccoes = scanner.nextInt();
            
            BuySharesIdea buySharesIdea = new BuySharesIdea(user, referencia, numAccoes);
            
            out.writeObject(buySharesIdea);
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean deleteIdeia(User user){
        try {
            
            System.out.println("Introduza a Referencia da Ideia: ");
            int referencia = scanner.nextInt();
            
            out.writeObject(new DeleteIdeia(user, referencia));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public boolean orderToSellShare(User user){
        try {
            System.out.println("Introduza a Referencia da Ideia: ");
            int referencia = scanner.nextInt();
            
            OrderToSellShare sellShare = new OrderToSellShare();
            sellShare.setReferenciaIdeia(referencia);
            sellShare.setUser(user);
            
            out.writeObject(sellShare);
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean makeOfferBuyShares(User user){
        try {
            System.out.println("Introduza a Referencia da Ideia: ");
            int referencia = scanner.nextInt();
            System.out.println("Introduza a quantidade de accoes que deseja comprar: ");
            int numAccoes = scanner.nextInt();
            System.out.println("Introduza o valor por accoes que deseja oferecer: ");
            double valorPorAccao = scanner.nextDouble();
            MakeOfferBuyShares offerBuyShares = new MakeOfferBuyShares();
            offerBuyShares.setReferenciaIdeia(referencia);
            offerBuyShares.setNumAccoes(numAccoes);
            offerBuyShares.setValorPorAccao(valorPorAccao);
            offerBuyShares.setUser(user);
            
            out.writeObject(offerBuyShares);
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public boolean getAllMyActiveShares(User user){
        try {
            out.writeObject(new MyActiveShares(user));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean setSharesSellingPrice(User user){
        try {
            System.out.println("Introduza a Referencia da Ideia da Accao: ");
            int referencia = scanner.nextInt();
            System.out.println("Introduza o novo valor de venda da accao: ");
            double novoValor = scanner.nextDouble();
            
            out.writeObject(new SharesSellingPrice(user,referencia,novoValor));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    public boolean showTransactionHistory(User user){
        try {
            out.writeObject(new ShowTransactionHistory(user));
            Values.ClientMenu();
            return true;
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }
}
