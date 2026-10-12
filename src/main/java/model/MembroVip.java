package model;

import java.util.List;

/**
 * The type Membro vip.
 */
public class MembroVip extends Iscritto {
    private List<ServizioWellness> serviziPrenotati;


    /**
     * Instantiates a new Membro vip.
     *
     * @param id_Utente         the id utente
     * @param nome              the nome
     * @param cognome           the cognome
     * @param username          the username
     * @param password          the password
     * @param pagamenti         the pagamenti
     * @param schedaAllenamento the scheda allenamento
     * @param partecipazioni    the partecipazioni
     * @param serviziPrenotati  the servizi prenotati
     */
    public MembroVip(String id_Utente, String nome, String cognome, String username, String password, List<Pagamento> pagamenti, SchedaAllenamento schedaAllenamento, List<Partecipa> partecipazioni, List<ServizioWellness> serviziPrenotati) {
        super(id_Utente, nome, cognome, username, password, pagamenti, schedaAllenamento, partecipazioni);
        this.serviziPrenotati = serviziPrenotati;
    }

    /**
     * Gets servizi prenotati.
     *
     * @return the servizi prenotati
     */
    public List<ServizioWellness> getServiziPrenotati() { return serviziPrenotati; }

    /**
     * Sets servizi prenotati.
     *
     * @param serviziPrenotati the servizi prenotati
     */
    public void setServiziPrenotati(List<ServizioWellness> serviziPrenotati) { this.serviziPrenotati = serviziPrenotati; }
}