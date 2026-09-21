package dao;

import model.Pagamento;

import java.util.List;

public interface PagamentoDAO {
    public void salva(Pagamento pagamento );
    Pagamento cercaPerId_Pagamento(String id_pagamento);
    List<Pagamento> trovaTutti();
    void aggiornaPagamento(Pagamento pagamento);
}
