package objectos;


import java.io.Serializable;

public class User implements Serializable {

    private int idUtilizador = 0;
    private String username;
    private String passw;
    private String nome;
    private int idade;
    private double deicoins;
    private String pais;
    private String email;
    private boolean online;
    
    public User() {
        this.online = false;
        
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getDeicoins() {
        return deicoins;
    }

    public void setDeicoins(double deicoins) {
        this.deicoins = deicoins;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void cloneUser(User user){
        this.idUtilizador = user.getIdUtilizador();
        this.username = user.getUsername();
        this.passw = user.getPassw();
        this.nome = user.getNome();
        this.idade = user.getIdade();
        this.deicoins = user.getDeicoins();
        this.pais = user.getPais();
        this.email = user.getEmail();
        this.online = user.isOnline();
    }
    @Override
    public String toString() {
        return "User{" + "idUtilizador=" + idUtilizador + ", username=" + username + ", passw=" + passw + ", nome=" + nome + ", idade=" + idade + ", deicoins=" + deicoins + ", pais=" + pais + ", email=" + email + ", online=" + online + '}';
    }

   
}
