package dao;

import model.Corso;

import java.util.List;

public interface CorsoDAO {
    public void salva(Corso corso);
    Corso cercaPerNomeCorso(String nomeCorso);
    Corso cercaPerId_Corso(String id_Corso);
    List<Corso> trovaTutti();
    void aggiornaCorso(Corso corso);

}
