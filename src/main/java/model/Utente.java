package model;//package model;


public class Utente
{
    private String idUtente;

    public String getIdUtente() { return idUtente; }

    public void setIdUtente(String idUtente) { this.idUtente = idUtente; }

    private String login;
    private String email;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    private String password;
    private String nome;
    private String cognome;

    public Utente(String login, String password, String nome, String cognome)
    {
        this.login = login;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }


    public String getNome() { return nome;}

    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }

    public void setCognome(String nome) { this.cognome = cognome; }

    public String toString() { return " " +nome+ ", " +cognome; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
