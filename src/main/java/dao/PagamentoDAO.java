package dao;

import model.Pagamento;

public interface PagamentoDAO {
    void salva(Pagamento pagamento);
    Pagamento cercaPerId(int idPagamento);
    void aggiornaStato(int idPagamento, Pagamento.StatoPag nuovoStato);
}
