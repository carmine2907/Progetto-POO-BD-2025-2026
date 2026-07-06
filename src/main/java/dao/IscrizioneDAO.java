package dao;

import model.Iscrizione;

/**
 * The interface Iscrizione dao.
 */
public interface IscrizioneDAO {
    /**
     * Salva.
     *
     * @param iscrizione the iscrizione
     */
    void salva(Iscrizione iscrizione);

    /**
     * Cerca per id iscrizione.
     *
     * @param idIscrizione the id iscrizione
     * @return the iscrizione
     */
    Iscrizione cercaPerId(int idIscrizione);

    /**
     * Aggiorna validita.
     *
     * @param idIscrizione the id iscrizione
     * @param valida       the valida
     */
    void aggiornaValidita(int idIscrizione, boolean valida);
}