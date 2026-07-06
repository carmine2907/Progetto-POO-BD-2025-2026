package model;

/**
 * The type Allenatore.
 */
public class Allenatore extends Utente
{
    private String qualifica;

    /**
     * Instantiates a new Allenatore.
     *
     * @param login     the login
     * @param password  the password
     * @param nome      the nome
     * @param cognome   the cognome
     * @param qualifica the qualifica
     */
    public Allenatore (String login, String password, String nome, String cognome, String qualifica)
    {
        super(login, password, nome, cognome);
        this.qualifica = qualifica;
    }

    /**
     * Gets qualifica.
     *
     * @return the qualifica
     */
    public String getQualifica() { return qualifica; }

    /**
     * Sets qualifica.
     *
     * @param qualifica the qualifica
     */
    public void setQualifica(String qualifica) { this.qualifica = qualifica; }

    public String toString() {
        return super.toString() +qualifica;
    }
}
