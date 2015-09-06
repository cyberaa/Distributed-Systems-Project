/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientWeb;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import objectos.Ideia;
import objectos.Tema;
import objectos.User;
import objectos.operacoes.CreateIdeia;
import objectos.operacoes.CreateTopic;
import objectos.operacoes.Login;
import objectos.operacoes.Logout;
import objectos.operacoes.Registo;

/**
 *
 * @author jaimecruz
 */
public class ClientOperations implements Serializable {

    private Scanner scanner;
    private User user;
    private static List<CreateTopic> bufferTopicos = new ArrayList<>();
    private static List<CreateIdeia> bufferIdeias = new ArrayList<>();

    public ClientOperations(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
    }

    /**
     * @return the scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public User doLogin() {
        Login login = new Login(user);
        
        return user;

    }

    public User doLogin(User user) {
        Login login = new Login(user);
        login.setLogged(true);
        return user;

    }

    public void doRegisto() {
        User user = new User();
        Registo registo = new Registo(user);

    }

    public boolean doLogout(User user) {
        Logout logout = new Logout(user);
        return true;

    }

    public CreateTopic createTopic(User user) {
        Tema tema = new Tema();
        CreateTopic createTopic = null;
        createTopic = new CreateTopic(tema, user);
        return createTopic;
    }

    public boolean getAllTopic() {
        return false;
    }

    public boolean getAllMyTopic(User user) {
        return false;
    }

    public boolean deleteTopic(User user) {
        return false;
    }

    public CreateIdeia createIdeia(User user) {
        Ideia ideia = new Ideia();
        CreateIdeia createIdeia = null;
        
        return createIdeia;
    }

    public boolean getAllIdeiaFromTopic() {
        return false;
    }

    public boolean getAllMyIdeias(User user) {
        return false;
    }

    public boolean buySharesOfAnIdeia(User user) {
        return false;
    }

    public boolean deleteIdeia(User user) {
        return false;
    }

    public boolean transactionalTrading(User user) {
        return false;
    }

    public boolean getAllMyActiveShares(User user) {
        return false;
    }

    public boolean setSharesSellingPrice(User user) {
        return false;
    }

    public boolean showTransactionHistory(User user) {
        return false;
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
                    List<CreateTopic> lista = (List<CreateTopic>)oi.readObject();;
                    if(lista!=null){
                        for (CreateTopic createTopic : lista) {
                            bufferTopicos.add(createTopic);
                        }
                    }
                      
                } else if (tipo == 2) {
                    List<CreateIdeia> lista = (List<CreateIdeia>)oi.readObject();;
                    if(lista!=null){
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
