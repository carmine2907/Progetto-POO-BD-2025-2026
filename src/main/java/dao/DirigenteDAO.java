package dao;

import model.Dirigente;
import java.util.List;

/**
 * The interface Dirigente dao.
 */
public interface DirigenteDAO {
    /**
     * Salva.
     *
     * @param dirigente the dirigente
     */
    void salva(Dirigente dirigente);

    /**
     * Cerca per id dirigente.
     *
     * @param id the id
     * @return the dirigente
     */
    Dirigente cercaPerId(int id);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Dirigente> trovaTutti();

    /**
     * Elimina.
     *
     * @param id the id
     */
    void elimina(int id);
}
