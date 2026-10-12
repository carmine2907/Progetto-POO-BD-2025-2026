package dao;

import model.Utente;


import java.util.List;


/**
 * The interface Utente dao.
 */
public interface UtenteDAO {
    /**
     * Salva.
     *
     * @param utente the utente
     */
    public void salva(Utente utente);

    /**
     * Cerca per username utente.
     *
     * @param username the username
     * @return the utente
     */
    Utente cercaPerUsername(String username);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Utente> trovaTutti();

    /**
     * Aggiorna utente.
     *
     * @param utente the utente
     */
    void aggiornaUtente(Utente utente);

    /**
     * Cerca per id utente.
     *
     * @param idUtente the id utente
     * @return the utente
     */
    Utente cercaPerId(String idUtente);
    }

