package dao;

import model.Campo;
import java.util.List;

public interface CampoDAO {
    void salva(Campo campo);
    Campo cercaPerId(int idCampo);
    List<Campo> trovaTutti();
    void aggiornaDisponibilita(int idCampo, boolean disponibile);
}