package dao;

import model.Pagamento;

/**
 * The interface Pagamento dao.
 */
public interface PagamentoDAO {
    /**
     * Salva.
     *
     * @param pagamento the pagamento
     */
    void salva(Pagamento pagamento);

    /**
     * Cerca per id pagamento.
     *
     * @param idPagamento the id pagamento
     * @return the pagamento
     */
    Pagamento cercaPerId(int idPagamento);

    /**
     * Aggiorna stato.
     *
     * @param idPagamento the id pagamento
     * @param nuovoStato  the nuovo stato
     */
    void aggiornaStato(int idPagamento, Pagamento.StatoPag nuovoStato);

    /**
     * Gets ultimo stato pagamento.
     *
     * @param idAtleta the id atleta
     * @return the ultimo stato pagamento
     */
    String getUltimoStatoPagamento(int idAtleta);
}
