package dao;





import model.Istruttore;

import java.util.List;

/**
 * The interface Istruttore dao.
 */
public interface IstruttoreDAO {
    /**
     * Salva.
     *
     * @param istruttore the istruttore
     */
    public void salva(Istruttore istruttore);

    /**
     * Cerca per username istruttore.
     *
     * @param username the username
     * @return the istruttore
     */
    Istruttore cercaPerUsername(String username);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Istruttore> trovaTutti();

    /**
     * Aggiorna istruttore.
     *
     * @param istruttore the istruttore
     */
    void aggiornaIstruttore(Istruttore istruttore );
}
