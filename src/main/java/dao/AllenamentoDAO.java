package dao;

import model.Allenamento;
import model.Atleta;
import java.util.List;

/**
 * The interface Allenamento dao.
 */
public interface AllenamentoDAO {
    /**
     * Salva.
     *
     * @param allenamento the allenamento
     */
    void salva(Allenamento allenamento);

    /**
     * Cerca per id allenamento.
     *
     * @param idAllenamento the id allenamento
     * @return the allenamento
     */
    Allenamento cercaPerId(int idAllenamento);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Allenamento> trovaTutti();

    /**
     * Elimina.
     *
     * @param idAllenamento the id allenamento
     */
    void elimina(int idAllenamento);

    /**
     * Salva presenza.
     *
     * @param allenamento the allenamento
     * @param atleta      the atleta
     */
    void salvaPresenza(Allenamento allenamento, Atleta atleta);
}
