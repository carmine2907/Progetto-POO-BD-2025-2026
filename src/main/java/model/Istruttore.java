package model;

import java.util.List;

/**
 * The type Istruttore.
 */
public class Istruttore extends Utente {
    private List<Corso> corsiGestiti;
    private List<SchedaAllenamento> schedeCreate;


    /**
     * Instantiates a new Istruttore.
     *
     * @param id_utente    the id utente
     * @param nome         the nome
     * @param cognome      the cognome
     * @param username     the username
     * @param password     the password
     * @param corsiGestiti the corsi gestiti
     * @param schedeCreate the schede create
     */
    public Istruttore(String id_utente, String nome, String cognome, String username, String password, List<Corso> corsiGestiti, List<SchedaAllenamento> schedeCreate) {
        super(id_utente,  password, username,  nome,  cognome);
        this.corsiGestiti = corsiGestiti;
        this.schedeCreate = schedeCreate;
    }

    /**
     * Gets corsi gestiti.
     *
     * @return the corsi gestiti
     */
    public List<Corso> getCorsiGestiti() { return corsiGestiti; }

    /**
     * Sets corsi gestiti.
     *
     * @param corsiGestiti the corsi gestiti
     */
    public void setCorsiGestiti(List<Corso> corsiGestiti) { this.corsiGestiti = corsiGestiti; }

    /**
     * Gets schede create.
     *
     * @return the schede create
     */
    public List<SchedaAllenamento> getSchedeCreate() { return schedeCreate; }

    /**
     * Sets schede create.
     *
     * @param schedeCreate the schede create
     */
    public void setSchedeCreate(List<SchedaAllenamento> schedeCreate) { this.schedeCreate = schedeCreate; }

}