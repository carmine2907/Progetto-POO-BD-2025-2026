package model;

import java.util.List;

/**
 * The type Iscritto.
 */
public class Iscritto extends Utente {
    private List<Pagamento> pagamenti;
    private SchedaAllenamento schedaAllenamento;
    private List<Partecipa> partecipazioni;


    /**
     * Instantiates a new Iscritto.
     *
     * @param id_utente         the id utente
     * @param nome              the nome
     * @param cognome           the cognome
     * @param username          the username
     * @param password          the password
     * @param pagamenti         the pagamenti
     * @param schedaAllenamento the scheda allenamento
     * @param partecipazioni    the partecipazioni
     */
    public Iscritto(String id_utente, String nome, String cognome, String username, String password, List<Pagamento> pagamenti, SchedaAllenamento schedaAllenamento, List<Partecipa> partecipazioni) {
        super(id_utente, nome, cognome, username,password);
        this.pagamenti = pagamenti;
        this.schedaAllenamento = schedaAllenamento;
        this.partecipazioni = partecipazioni;
    }

    /**
     * Gets pagamenti.
     *
     * @return the pagamenti
     */
    public List<Pagamento> getPagamenti() { return pagamenti; }

    /**
     * Sets pagamenti.
     *
     * @param pagamenti the pagamenti
     */
    public void setPagamenti(List<Pagamento> pagamenti) { this.pagamenti = pagamenti; }

    /**
     * Gets scheda allenamento.
     *
     * @return the scheda allenamento
     */
    public SchedaAllenamento getSchedaAllenamento() { return schedaAllenamento; }

    /**
     * Sets scheda allenamento.
     *
     * @param schedaAllenamento the scheda allenamento
     */
    public void setSchedaAllenamento(SchedaAllenamento schedaAllenamento) { this.schedaAllenamento = schedaAllenamento; }

    /**
     * Gets partecipazioni.
     *
     * @return the partecipazioni
     */
    public List<Partecipa> getPartecipazioni() { return partecipazioni; }

    /**
     * Sets partecipazioni.
     *
     * @param partecipazioni the partecipazioni
     */
    public void setPartecipazioni(List<Partecipa> partecipazioni) { this.partecipazioni = partecipazioni; }

}