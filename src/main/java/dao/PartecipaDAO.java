package dao;


import model.Utente;
import model.Partecipa;
import java.util.List;

/**
 * The interface Partecipa dao.
 */
public interface PartecipaDAO {
    /**
     * Salva.
     *
     * @param partecipa the partecipa
     */
    public void salva(Partecipa partecipa );

    /**
     * Cerca per id partecipa.
     *
     * @param id_corso    the id corso
     * @param id_iscritto the id iscritto
     * @return the partecipa
     */
    Partecipa cercaPerId (String id_corso, String id_iscritto );

    /**
     * Aggiorna partecipazione.
     *
     * @param partecipa the partecipa
     */
    void aggiornaPartecipazione(Partecipa partecipa );

    /**
     * Trova partecipazioni per corso list.
     *
     * @param idCorso the id corso
     * @return the list
     */
    List<Partecipa> trovaPartecipazioniPerCorso(String idCorso);

    /**
     * Trova partecipazioni per iscritto list.
     *
     * @param idIscritto the id iscritto
     * @return the list
     */
    List<Partecipa> trovaPartecipazioniPerIscritto(String idIscritto);

}
