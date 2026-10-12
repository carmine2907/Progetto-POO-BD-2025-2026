package dao;

import model.Iscritto;


import java.util.List;

/**
 * The interface Iscritto dao.
 */
public interface IscrittoDAO {
    /**
     * Salva.
     *
     * @param iscritto the iscritto
     */
    public void salva(Iscritto iscritto);

    /**
     * Cerca per username iscritto.
     *
     * @param username the username
     * @return the iscritto
     */
    Iscritto cercaPerUsername(String username);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Iscritto> trovaTutti();

    /**
     * Aggiorna iscritto.
     *
     * @param iscritto the iscritto
     */
    void aggiornaIscritto(Iscritto iscritto);
}
