package dao;

import model.Squadra;
import model.Atleta;
import java.util.List;

/**
 * The interface Squadra dao.
 */
public interface SquadraDAO {
    /**
     * Assegna atleta.
     *
     * @param squadra the squadra
     * @param atleta  the atleta
     * @throws Exception the exception
     */
// Aggiorna il legame tra un atleta e una squadra
    void assegnaAtleta(Squadra squadra, Atleta atleta) throws Exception;

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Squadra> trovaTutti();
}