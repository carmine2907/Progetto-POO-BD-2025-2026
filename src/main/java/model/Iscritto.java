package model;

import java.util.List;

public class Iscritto extends Utente {
    private List<Pagamento> pagamenti;
    private SchedaAllenamento schedaAllenamento;
    private List<Partecipa> partecipazioni;


    public Iscritto(String id_Utente, String nome, String cognome, String username, String password, List<Pagamento> pagamenti, SchedaAllenamento schedaAllenamento, List<Partecipa> partecipazioni) {
        super(id_Utente, nome, cognome, username, password);
        this.pagamenti = pagamenti;
        this.schedaAllenamento = schedaAllenamento;
        this.partecipazioni = partecipazioni;
    }

    public List<Pagamento> getPagamenti() { return pagamenti; }
    public void setPagamenti(List<Pagamento> pagamenti) { this.pagamenti = pagamenti; }

    public SchedaAllenamento getSchedaAllenamento() { return schedaAllenamento; }
    public void setSchedaAllenamento(SchedaAllenamento schedaAllenamento) { this.schedaAllenamento = schedaAllenamento; }

    public List<Partecipa> getPartecipazioni() { return partecipazioni; }
    public void setPartecipazioni(List<Partecipa> partecipazioni) { this.partecipazioni = partecipazioni; }
}