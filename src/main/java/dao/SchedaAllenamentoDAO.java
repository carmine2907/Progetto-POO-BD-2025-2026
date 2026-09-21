package dao;

import java.util.List;
import model.SchedaAllenamento;
public interface SchedaAllenamentoDAO {
    public void salva(SchedaAllenamento schedaAllenamento );
    SchedaAllenamento cercaPerId_Scheda(String id_Scheda);
    SchedaAllenamento cercaPerid_Iscritto(String id_iscritto);
    List<SchedaAllenamento> trovaTutti();
    void aggiornaScheda(SchedaAllenamento schedaAllenamento);
}
