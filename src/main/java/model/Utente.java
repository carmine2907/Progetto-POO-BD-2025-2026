package model;

/**
 * The type Utente.
 */
public class Utente {
    private  String username;
    private String password;
    private String id_utente;
    private String nome,cognome;

    public Utente(String username, String password, String id_utente, String nome, String cognome) {
        this.username = username;
        this.password = password;
        this.id_utente = id_utente;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_utente() {
        return id_utente;
    }

    public void setId_utente(String id_utente) {
        this.id_utente = id_utente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
