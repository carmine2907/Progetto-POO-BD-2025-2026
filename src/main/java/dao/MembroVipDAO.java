package dao;

import model.MembroVip;


import java.util.List;

/**
 * The interface Membro vip dao.
 */
public interface MembroVipDAO {
    /**
     * Salva.
     *
     * @param membroVip the membro vip
     */
    public void salva(MembroVip membroVip );

    /**
     * Cerca per username membro vip.
     *
     * @param username the username
     * @return the membro vip
     */
    MembroVip cercaPerUsername(String username);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<MembroVip> trovaTutti();

    /**
     * Aggiorna membro.
     *
     * @param membroVip the membro vip
     */
    void aggiornaMembro(MembroVip membroVip);
}
