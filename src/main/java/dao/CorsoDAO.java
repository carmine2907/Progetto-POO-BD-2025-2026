package dao;

import model.Corso;

import java.util.List;

/**
 * The interface Corso dao.
 */
public interface CorsoDAO {
    /**
     * Salva.
     *
     * @param corso the corso
     */
    public void salva(Corso corso);

    /**
     * Cerca per nome corso corso.
     *
     * @param nomeCorso the nome corso
     * @return the corso
     */
    Corso cercaPerNomeCorso(String nomeCorso);

    /**
     * Cerca per id corso corso.
     *
     * @param id_Corso the id corso
     * @return the corso
     */
    Corso cercaPerId_Corso(String id_Corso);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Corso> trovaTutti();

    /**
     * Aggiorna corso.
     *
     * @param corso the corso
     */
    void aggiornaCorso(Corso corso);

    /**
     * Conta iscritti al corso int.
     *
     * @param id_Corso the id corso
     * @return the int
     */
    public int contaIscrittiAlCorso(String id_Corso);
}
