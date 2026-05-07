package model;//package model;


public class Utente
{
    private String login;
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


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String nome) { this.cognome = cognome; }

    public String toString() { return " " +nome+ ", " +cognome; }
}
