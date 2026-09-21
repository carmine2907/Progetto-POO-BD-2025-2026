package dao;

import model.ServizioWellness;

import java.util.List;

public interface ServizioWellnessDAO {
    public void salva(ServizioWellness servizioWellness );
    ServizioWellness cercaPerId(String id_ServizioWellness);
    List<ServizioWellness> trovaTutti();
    void aggiornaServizioWellness(ServizioWellness servizioWellness);
}
