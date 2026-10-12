package model;

/**
 * The type Utente.
 */
public class Utente {
    private  String username;
    private String password;
    private String id_utente;
    private String nome,cognome;

    /**
     * Instantiates a new Utente.
     *
     * @param id_utente the id utente
     * @param nome      the nome
     * @param cognome   the cognome
     * @param username  the username
     * @param password  the password
     */
    public Utente( String id_utente, String nome, String cognome, String username,String password) {
        this.id_utente = id_utente;
        this.nome = nome;
        this.cognome = cognome;
        this.username= username;
        this.password=password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets id utente.
     *
     * @return the id utente
     */
    public String getId_utente() {
        return id_utente;
    }

    /**
     * Sets id utente.
     *
     * @param id_utente the id utente
     */
    public void setId_utente(String id_utente) {
        this.id_utente = id_utente;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "username='" + username + '\'' +
                '}';
    }
}
