package clients;

import clients.rmi.ClientInterface;
import clients.rmi.ClientInterfaceImpl;
import objectos.User;
import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import objectos.operacoes.CreateIdeia;
import objectos.operacoes.CreateTopic;
import servers.rmi.RMI_Interface;
import suporte.Values;

public class TCPClient implements Serializable {

    private static boolean flag = true;
    private User user;
    private Socket s = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int porto;
    private Scanner scanner = null;
    private String texto, host;
    private boolean isLogged = false, OK = true, stop = false;
    private boolean commandFlag = true;
    private boolean firstTime = true;
    private OperacoesCliente operacoesCliente;
    private Read read;
    private RMI_Interface rMI_Interface;
    private ThreadLimpaBuffer threadLimpaBuffer;
    private static List<CreateTopic> bufferTopicos = new ArrayList<>();
    private static List<CreateIdeia> bufferIdeias = new ArrayList<>();

    public TCPClient(String host, int porto) {
        this.porto = porto;
        this.host = host;
        executa();
    }

    private void executa() {
        try {
            //this.porto = 6000;
            this.s = new Socket(host, porto);
            if (this.s == null) {
                System.out.println("NAO CONSEGUIU CRIAR SOCKET");
            }
            System.out.println(this.s.getLocalPort());
            System.out.println("Socket = " + s);

            this.in = new ObjectInputStream(this.s.getInputStream());
            this.out = new ObjectOutputStream(this.s.getOutputStream());

            this.scanner = new Scanner(System.in);
            //System.out.println("Entrou");
            operacoesCliente = new OperacoesCliente(out, in, scanner);
            Values.menu();

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Lost connection… working on it… ");
            //Servidor em baixo reconectar
            reconnect();
        }
        if (isOK()) {
            this.read = new Read(in, this);
            this.threadLimpaBuffer = new ThreadLimpaBuffer(this);
            while (!stop) {
                texto = scanner.nextLine();
                if (!isLogged) {
                    switch (texto) {
                        case "1":
                            if (commandFlag) {
                                this.user = operacoesCliente.doLogin();
                                if (this.user != null && this.user.isOnline()) {
                                    loadBuffer(1, user);// buffer de topicos
                                    loadBuffer(2, user);// buffer de ideias

                                    if (!threadLimpaBuffer.isRunning()) {
                                        threadLimpaBuffer.setDaemon(false);
                                        threadLimpaBuffer.start();
                                    }
                                    try {
                                        initRMIInterface();
                                        if (rMI_Interface != null) {
                                            ClientInterface ci = new ClientInterfaceImpl();
                                            rMI_Interface.operationJoin(this.user, ci);
                                        }
                                    } catch (NotBoundException ex) {
                                        Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (MalformedURLException ex) {
                                        Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (RemoteException ex) {
                                        Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    isLogged = true;
                                }
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.menu();
                            }

                            break;
                        case "2":
                            if (commandFlag) {
                                operacoesCliente.doRegisto();
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.menu();
                            }
                            break;
                        case "3":
                            System.exit(0);
                            break;
                    }
                } else if (isLogged) {

                    if (!read.isRunning()) {
                        read.setDaemon(false);
                        read.start();
                    }
                    switch (texto) {
                        case "1":
                            CreateTopic topic = operacoesCliente.createTopic(this.user);
                            if (!commandFlag) {
                                adicionaNoBufferTopicos(topic);
                                Values.ClientMenu();
                            }
                            break;
                        case "2":
                            if (commandFlag) {
                                operacoesCliente.getAllTopic();
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "3":
                            if (commandFlag) {
                                operacoesCliente.getAllMyTopic(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "4":
                            if (commandFlag) {
                                operacoesCliente.deleteTopic(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "5":
                            CreateIdeia ideia = operacoesCliente.createIdeia(this.user);
                            if (!commandFlag) {
                                adicionaNoBufferIdeias(ideia);
                                Values.ClientMenu();
                            }
                            break;
                        case "6":
                            if (commandFlag) {
                                operacoesCliente.getAllIdeiaFromTopic();
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "7":
                            if (commandFlag) {
                                operacoesCliente.deleteIdeia(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "8":
                            if (commandFlag) {
                                operacoesCliente.buySharesOfAnIdeia(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "9":
                            if (commandFlag) {
                                operacoesCliente.orderToSellShare(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "10":
                            if (commandFlag) {
                                operacoesCliente.makeOfferBuyShares(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "11":
                            if (commandFlag) {
                                operacoesCliente.getAllMyActiveShares(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "12":
                            if (commandFlag) {
                                operacoesCliente.setSharesSellingPrice(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "13":
                            if (commandFlag) {
                                operacoesCliente.showTransactionHistory(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "14":
                            if (commandFlag) {
                                operacoesCliente.getAllMyIdeias(this.user);
                            } else {
                                System.out.println("Sorry! This command is unavailable, please try later...");
                                Values.ClientMenu();
                            }
                            break;
                        case "15":
                            if (commandFlag) {
                                this.user.setOnline(false);
                                if (operacoesCliente.doLogout(this.user)) {
                                    System.out.println("Bye Bye...");
                                    if (read.isRunning()) {
                                        read.setStop(false);
                                    }
                                    stop = true;
                                    saveBuffer(1, user);// buffer de topicos
                                    saveBuffer(2, user);// buffer de ideias
                                    System.exit(0);
                                }
                            } else {
                                if (read.isRunning()) {
                                    read.setStop(true);
                                }
                                if (threadLimpaBuffer.isRunning()) {
                                    threadLimpaBuffer.setStop(true);
                                }
                                stop = true;
                                saveBuffer(1, user);// buffer de topicos
                                saveBuffer(2, user);// buffer de ideias
                                System.exit(0);
                            }
                            break;
                    }

                }
            }
        } else {
            System.out.println("Servidores indisponiveis. Por favor, tente mais tarde");
        }

    }

    private void initRMIInterface() throws NotBoundException, MalformedURLException, RemoteException {
        rMI_Interface = (RMI_Interface) Naming.lookup("rmi://localhost:7500/dbs");
    }

    public void reconnect() {
        setCommandFlag(false);
        String porto = null;
        long inicio = System.currentTimeMillis();
        boolean flag_ = true;
        while (flag_) {
            String serv = getServer(); // funcção que devolve um host e porto válido
            if (serv != null && serv.length() > 0) {
                porto = serv.split(" ")[0].split(":")[1];

            }
            if (porto != null) {
                try {
                    // cria novas ligacoes;
                    int port = Integer.parseInt(porto);
                    this.s = new Socket(host, port);
                    this.in = new ObjectInputStream(this.s.getInputStream());
                    this.out = new ObjectOutputStream(this.s.getOutputStream());

                    scanner = new Scanner(System.in);

                    // actualiza as variaveis
                    operacoesCliente.setS(s);
                    operacoesCliente.setIn(in);
                    operacoesCliente.setOut(out);
                    operacoesCliente.setScanner(scanner);

                    // testar/actualizar a interface de cliente
                    try {
                        String str = rMI_Interface.sayHello();
                        if (str == null) {
                            initRMIInterface();
                            ClientInterface ci = new ClientInterfaceImpl();
                            rMI_Interface.operationJoin(this.user, ci);
                        }
                    } catch (Exception e) {
                    }

                    // actualiza a thread que le as respostas do TCPSERVER
                    if (read.isRunning()) {
                        read.setStop(true);
                        read.setIn(in);
                        read.setRunning(false);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (!read.isRunning()) {
                            read = new Read(in, this);
                            read.setDaemon(false);
                            read.start();
                        }
                    } else {
                        read = new Read(in, this);
                        read.setDaemon(false);
                        read.start();
                    }
                    if (!isIsLogged()) {
                        user = operacoesCliente.doLogin();
                    } else {
                        user = operacoesCliente.doLogin(user);
                    }
                    if (this.user != null && this.user.isOnline()) {
                        isLogged = true;
                    }
                    flag_ = false;
                    setCommandFlag(true);

                } catch (UnknownHostException ex) {
                    Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    this.s = null;
                    this.in = null;
                    this.out = null;
                    //Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (isFirstTime()) {
                if (System.currentTimeMillis() - inicio > 20000) {
                    flag_ = false;
                    System.err.println("!!!Não existe nenhum servidor disponível, por favor tente mais tarde!!!");
                    System.exit(0);
                }
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public synchronized static void adicionaNoBufferTopicos(CreateTopic createTopic) {
        bufferTopicos.add(createTopic);
    }

    public synchronized void esvaziarBufferTopicos() {
        if (!bufferTopicos.isEmpty()) {
            List<CreateTopic> listaKeysParaRemoverDoBuffer = new ArrayList<>();

            for (CreateTopic topic : bufferTopicos) {
                if (sendMensagem(topic, out)) {
                    listaKeysParaRemoverDoBuffer.add(topic);
                }
            }
            for (CreateTopic topic : listaKeysParaRemoverDoBuffer) {
                bufferTopicos.remove(topic);
            }
        }
    }

    public synchronized static void adicionaNoBufferIdeias(CreateIdeia createIdeia) {
        bufferIdeias.add(createIdeia);
    }

    public synchronized void esvaziarBufferIdeias() {
        if (!bufferIdeias.isEmpty()) {
            List<CreateIdeia> listaKeysParaRemoverDoBuffer = new ArrayList<>();

            for (CreateIdeia ideia : bufferIdeias) {
                if (sendMensagem(ideia, out)) {
                    listaKeysParaRemoverDoBuffer.add(ideia);
                }
            }
            for (CreateIdeia ideia : listaKeysParaRemoverDoBuffer) {
                bufferIdeias.remove(ideia);
            }
        }
    }

    private static boolean sendMensagem(Object obj, ObjectOutputStream out) {
        try {
            out.writeObject(obj);
            return true;
        } catch (IOException ex) {
            //ex.printStackTrace();
            return false;
        }

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

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        TCPClient.flag = flag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isCommandFlag() {
        return commandFlag;
    }

    public void setCommandFlag(boolean commandFlag) {
        this.commandFlag = commandFlag;
    }

    public OperacoesCliente getOperacoesCliente() {
        return operacoesCliente;
    }

    public void setOperacoesCliente(OperacoesCliente operacoesCliente) {
        this.operacoesCliente = operacoesCliente;
    }

    public Read getRead() {
        return read;
    }

    public void setRead(Read read) {
        this.read = read;
    }

    public RMI_Interface getrMI_Interface() {
        return rMI_Interface;
    }

    public void setrMI_Interface(RMI_Interface rMI_Interface) {
        this.rMI_Interface = rMI_Interface;
    }

    public boolean isOK() {
        return OK;
    }

    public void setOK(boolean OK) {
        this.OK = OK;
    }

    // funcção que devolve um host e porto válido. É feita com uma ligação UDP
    private static String getServer() {
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

    public static void main(String args[]) {
        /**
         * *****************************************************************************************************
         * Este pedaço de codigo vai ao ControlServer.java e pede o porto onde
         * esta a correr o servidor master. Caso não for devolvido nenhum
         * servidor num periodo de 10 segundos(fica ao criterio do programador)
         * o programa Client pára a execução e mostra uma mensagem de erro.
         * *****************************************************************************************************
         */
        String porto = null;
        String host = null;
        long inicio = System.currentTimeMillis();
        while (flag) {
            String serv = getServer(); // funcção que devolve um host e porto válido
            if (serv != null && serv.length() > 0) {
                String aux[] = serv.split(" ");
                host = aux[0].split(":")[0];
                porto = aux[0].split(":")[1];
                flag = false;
                System.out.println("host = " + host + "\nporto = " + porto);
            }
            if (System.currentTimeMillis() - inicio > 10000) {
                flag = false;
                System.err.println("!!!Não existe nenhum servidor disponível, por favor tente mais tarde!!!");
                System.exit(0);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
//                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /**
         * *****************************************************************************************************
         */
        new TCPClient(host, Integer.parseInt(porto));

        //new TCPClient(6000);
    }
}//termina a classe
