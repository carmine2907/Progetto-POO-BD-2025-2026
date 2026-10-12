package model;

/**
 * The type Partecipa.
 */
public class Partecipa  {
    private String id_Corso;
    private String id_utente;

    private Corso corso;
    private Iscritto iscritto;


    /**
     * Instantiates a new Partecipa.
     *
     * @param id_Corso  the id corso
     * @param id_utente the id utente
     * @param corso     the corso
     * @param iscritto  the iscritto
     */
    public Partecipa(String id_Corso, String id_utente, Corso corso, Iscritto iscritto) {
        this.id_Corso = id_Corso;
        this.id_utente = id_utente;
        this.corso = corso;
        this.iscritto = iscritto;
    }

    /**
     * Gets id corso.
     *
     * @return the id corso
     */
    public String getId_Corso() { return id_Corso; }

    /**
     * Sets id corso.
     *
     * @param id_Corso the id corso
     */
    public void setId_Corso(String id_Corso) { this.id_Corso = id_Corso; }

    /**
     * Gets id utente.
     *
     * @return the id utente
     */
    public String getId_utente() { return id_utente; }

    /**
     * Sets id utente.
     *
     * @param id_utente the id utente
     */
    public void setId_utente(String id_utente) { this.id_utente = id_utente; }

    /**
     * Gets corso.
     *
     * @return the corso
     */
    public Corso getCorso() { return corso; }

    /**
     * Sets corso.
     *
     * @param corso the corso
     */
    public void setCorso(Corso corso) { this.corso = corso; }

    /**
     * Gets iscritto.
     *
     * @return the iscritto
     */
    public Iscritto getIscritto() { return iscritto; }

    /**
     * Sets iscritto.
     *
     * @param iscritto the iscritto
     */
    public void setIscritto(Iscritto iscritto) { this.iscritto = iscritto; }
}