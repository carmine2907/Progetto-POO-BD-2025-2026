package dao;

import model.Allenatore;
import java.util.List;

/**
 * The interface Allenatore dao.
 */
public interface AllenatoreDAO {
    /**
     * Salva.
     *
     * @param allenatore the allenatore
     */
    void salva(Allenatore allenatore);

    /**
     * Cerca per id allenatore.
     *
     * @param id the id
     * @return the allenatore
     */
    Allenatore cercaPerId(int id);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Allenatore> trovaTutti();

    /**
     * Elimina.
     *
     * @param id the id
     */
    void elimina(int id);
}