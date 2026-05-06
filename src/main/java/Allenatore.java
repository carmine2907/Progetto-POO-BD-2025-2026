public class Allenatore extends Utente
{
    private String qualifica;

    public Allenatore (String login, String passward, String nome, String cognome)
    {
        super(login, passward, nome, cognome);
        this.qualifica = qualifica;
    }

    public String getQulifica() { return qualifica; }
    public void setQualifica(String ruolo) { this.qualifica = qualifica; }

    public String toString() { return super.toString() +qualifica; }
}
