package model;

import java.util.List;

public class MembroVip extends Iscritto {
    private List<ServizioWellness> serviziPrenotati;



    public MembroVip(String id_Utente, String nome, String cognome, String username, String password, List<Pagamento> pagamenti, SchedaAllenamento schedaAllenamento, List<Partecipa> partecipazioni, List<ServizioWellness> serviziPrenotati) {
        super(id_Utente, nome, cognome, username, password, pagamenti, schedaAllenamento, partecipazioni);
        this.serviziPrenotati = serviziPrenotati;
    }

    public List<ServizioWellness> getServiziPrenotati() { return serviziPrenotati; }
    public void setServiziPrenotati(List<ServizioWellness> serviziPrenotati) { this.serviziPrenotati = serviziPrenotati; }
}