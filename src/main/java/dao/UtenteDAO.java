package dao;

import model.Utente;

/**
 * The interface Utente dao.
 */
public interface UtenteDAO {

    /**
     * Salva.
     *
     * @param utente the utente
     */
    void salva(Utente utente);

    /**
     * Cerca per username utente.
     *
     * @param login the login
     * @return the utente
     */
    Utente cercaPerUsername(String login);

}