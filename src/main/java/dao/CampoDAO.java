package dao;

import model.Campo;
import java.util.List;

/**
 * The interface Campo dao.
 */
public interface CampoDAO {
    /**
     * Salva.
     *
     * @param campo the campo
     */
    void salva(Campo campo);

    /**
     * Cerca per id campo.
     *
     * @param idCampo the id campo
     * @return the campo
     */
    Campo cercaPerId(int idCampo);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Campo> trovaTutti();

    /**
     * Aggiorna disponibilita.
     *
     * @param idCampo     the id campo
     * @param disponibile the disponibile
     */
    void aggiornaDisponibilita(int idCampo, boolean disponibile);
}