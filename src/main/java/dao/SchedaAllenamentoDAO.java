package dao;

import java.util.List;
import model.SchedaAllenamento;

/**
 * The interface Scheda allenamento dao.
 */
public interface SchedaAllenamentoDAO {
    /**
     * Salva.
     *
     * @param schedaAllenamento the scheda allenamento
     */
    public void salva(SchedaAllenamento schedaAllenamento );

    /**
     * Cerca per id scheda scheda allenamento.
     *
     * @param id_Scheda the id scheda
     * @return the scheda allenamento
     */
    SchedaAllenamento cercaPerId_Scheda(String id_Scheda);

    /**
     * Cerca perid iscritto scheda allenamento.
     *
     * @param id_iscritto the id iscritto
     * @return the scheda allenamento
     */
    SchedaAllenamento cercaPerid_Iscritto(String id_iscritto);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<SchedaAllenamento> trovaTutti();

    /**
     * Aggiorna scheda.
     *
     * @param schedaAllenamento the scheda allenamento
     */
    void aggiornaScheda(SchedaAllenamento schedaAllenamento);
}
