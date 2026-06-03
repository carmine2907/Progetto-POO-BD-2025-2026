package dao;

import model.Allenatore;
import java.util.List;

public interface AllenatoreDAO {
    void salva(Allenatore allenatore);
    Allenatore cercaPerId(int id);
    List<Allenatore> trovaTutti();
    void elimina(int id);
}