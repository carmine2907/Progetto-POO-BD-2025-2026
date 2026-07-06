package dao;

import model.Partita;

import java.util.List;

/**
 * The interface Partita dao.
 */
public interface PartitaDAO {
    /**
     * Salva.
     *
     * @param partita the partita
     */
    void salva(Partita partita);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Partita> trovaTutti();
}