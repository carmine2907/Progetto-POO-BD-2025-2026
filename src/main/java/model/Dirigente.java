package model;

/**
 * The type Dirigente.
 */
public class Dirigente extends Utente
{
    private String ruoloOrganizzativo;

    /**
     * Instantiates a new Dirigente.
     *
     * @param login              the login
     * @param password           the password
     * @param nome               the nome
     * @param cognome            the cognome
     * @param ruoloOrganizzativo the ruolo organizzativo
     */
    public Dirigente (String login, String password, String nome, String cognome, String ruoloOrganizzativo)
    {
        super(login, password, nome, cognome);
        this.ruoloOrganizzativo = ruoloOrganizzativo;
    }

    /**
     * Gets ruolo organizzativo.
     *
     * @return the ruolo organizzativo
     */
    public String getRuoloOrganizzativo() { return ruoloOrganizzativo; }

    /**
     * Sets ruolo organizzativo.
     *
     * @param ruoloOrganizzativo the ruolo organizzativo
     */
    public void setRuoloOrganizzativo(String ruoloOrganizzativo) {
        this.ruoloOrganizzativo = ruoloOrganizzativo;
    }

    public String toString() { return super.toString() + " Ruolo: " + ruoloOrganizzativo; }
}
