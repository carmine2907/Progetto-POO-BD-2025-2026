package dao;

import model.Pagamento;

import java.util.List;

/**
 * The interface Pagamento dao.
 */
public interface PagamentoDAO {
    /**
     * Salva.
     *
     * @param pagamento the pagamento
     */
    public void salva(Pagamento pagamento );

    /**
     * Cerca per id pagamento pagamento.
     *
     * @param id_pagamento the id pagamento
     * @return the pagamento
     */
    Pagamento cercaPerId_Pagamento(String id_pagamento);

    /**
     * Trova tutti list.
     *
     * @return the list
     */
    List<Pagamento> trovaTutti();

    /**
     * Aggiorna pagamento.
     *
     * @param pagamento the pagamento
     */
    void aggiornaPagamento(Pagamento pagamento);
}
