package model;

public class Allenatore extends Utente
{
    private String qualifica;

    public Allenatore (String login, String password, String nome, String cognome, String qualifica)
    {
        super(login, password, nome, cognome);
        this.qualifica = qualifica;
    }

    public String getQualifica() { return qualifica; }
    public void setQualifica(String qualifica) { this.qualifica = qualifica; }

    public String toString() { return super.toString() +qualifica; }
}
