package dao;

import model.ServizioWellness;

import java.util.List;

/**
 * The interface Servizio wellness dao.
 */
public interface ServizioWellnessDAO {
    /**
     * Salva.
     *
     * @param servizioWellness the servizio wellness
     */
    public void salva(ServizioWellness servizioWellness );

    /**
     * Cerca per id servizio wellness.
     *
     * @param id_ServizioWellness the id servizio wellness
     * @return the servizio wellness
     */
    ServizioWellness cercaPerId(String id_ServizioWellness);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<ServizioWellness> trovaTutti();

    /**
     * Aggiorna servizio wellness.
     *
     * @param servizioWellness the servizio wellness
     */
    void aggiornaServizioWellness(ServizioWellness servizioWellness);
}
