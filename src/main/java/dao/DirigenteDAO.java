package dao;

import model.Dirigente;
import java.util.List;

public interface DirigenteDAO {
    void salva(Dirigente dirigente);
    Dirigente cercaPerId(int id);
    List<Dirigente> trovaTutti();
    void elimina(int id);
}
