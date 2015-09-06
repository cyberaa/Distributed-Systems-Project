/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suporte;

/**
 *
 * @author Iris
 */
public class Values {
    //ACCOES - Estado //
    public static final String VENDIDA = "VENDIDO";
    public static final String CANCELADA = "CANCELADO";
    public static final String ACTIVO = "ACTIVO";
    public static final String INACTIVO = "INACTIVO";
    
    //ACCOES - Tipo de compra//
    public static final String AUTOMATICO = "AUTOMATICO";
    public static final String NAOAUTOMATICO = "NAOAUTOMATICO";
    
    //DEICOINS - Tipo de update 
    public static final String CREDITO = "CREDITO";
    public static final String DEBITO = "DEBITO";
    
    // TOPICOS
    public static final String TOPICO_OK = "TOPICO_OK";
    public static final String TOPICO_NOK = "TOPICO_NOK";
    public static final String TOPICO_DELETE_OK = "TOPICO_DELETE_OK";
    public static final String TOPICO_DELETE_NOK = "TOPICO_DELETE_NOK";
    
    //IDEIAS
    public static final String IDEIA_OK = "IDEIA_OK";
    public static final String IDEIA_NOK = "IDEIA_NOK";
    public static final String IDEIA_DELETE_OK = "IDEIA_DELETE_OK";
    public static final String IDEIA_DELETE_NOK = "IDEIA_DELETE_NOK";
    
    
    public static final String LOGIN_OK = "LOGIN_OK";
    public static final String ALREADY_LOGGED = "ALREADY_LOGGED";
    public static final String LOGIN_NOK = "LOGIN_NOK";
    public static final String LOGIN_RECONNECT = "LOGIN_RECONNECT";
    
    public static final String REGISTO_OK = "REGISTO_OK";
    public static final String REGISTO_NOK = "REGISTO_NOK";
    
    public static void ClientMenu(String username) {
        System.out.println("----------------------------");
        System.out.println("Bem Vindo " + username + "- IDEATRADER ...");
        System.out.println("1. Create a Topic ...");
        System.out.println("2. Show All Topics ...");
        System.out.println("3. Show My Topics ...");
        System.out.println("4. Delete a Topic ...");
        System.out.println("5. Submit an Idea ...");
        System.out.println("6. Show Ideas Inside a Topic ...");
        System.out.println("7. Delete an Idea ...");
        System.out.println("8. Buy Shares of an Idea ...");
        System.out.println("9. Order to Sell the Share for Best Offer...");
        System.out.println("10. Make an Offer to Buy Shares of an Idea...");
        System.out.println("12. See All My Active shares...");
        System.out.println("13. Set Shares Selling Price ...");
        System.out.println("14. Show Transaction History ...");
        System.out.println("15. See my ideas ...");
        System.out.println("16. Logout ...");
        
        System.out.println("----------------------------");

    }
    public static void ClientMenu() {
        System.out.println("----------------------------");
        System.out.println("1. Create a Topic ...");
        System.out.println("2. Show All Topics ...");
        System.out.println("3. Show My Topics ...");
        System.out.println("4. Delete a Topic ...");
        System.out.println("5. Submit an Idea ...");
        System.out.println("6. Show Ideas Inside a Topic ...");
        System.out.println("7. Delete an Idea ...");
        System.out.println("8. Buy shares of an Idea ...");
        System.out.println("9. Order to Sell Share for Best Offer...");
        System.out.println("10. Make an Offer to Buy Shares of an Idea...");
        System.out.println("11. See All My Active shares...");
        System.out.println("12. Set Shares Selling Price ...");
        System.out.println("13. Show Transaction History ...");
        System.out.println("14. See my ideas ...");
        System.out.println("15. Logout ...");
        
        System.out.println("----------------------------");

    }
    public static void menu() {
        System.out.println("----------------------------");
        System.out.println("Bem Vindo - IDEATRADER ...");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("----------------------------");
    }
}
