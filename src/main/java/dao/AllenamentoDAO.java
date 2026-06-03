package dao;

import model.Allenamento;
import model.Atleta;
import java.util.List;

public interface AllenamentoDAO {
    void salva(Allenamento allenamento);
    Allenamento cercaPerId(int idAllenamento);
    List<Allenamento> trovaTutti();
    void elimina(int idAllenamento);
    void salvaPresenza(Allenamento allenamento, Atleta atleta);
}
