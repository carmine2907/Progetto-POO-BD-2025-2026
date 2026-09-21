package dao;


import model.Utente;
import model.Partecipa;
import java.util.List;

public interface PartecipaDAO {
    public void salva(Partecipa partecipa );
    Partecipa cercaPerId (String id_corso, String id_iscritto );
    void aggiornaPartecipazione(Partecipa partecipa );
    List<Partecipa> trovaPartecipazioniPerCorso(String idCorso);
    List<Partecipa> trovaPartecipazioniPerIscritto(String idIscritto);
}
