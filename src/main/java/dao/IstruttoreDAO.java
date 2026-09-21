package dao;





import model.Istruttore;

import java.util.List;

public interface IstruttoreDAO {
    public void salva(Istruttore istruttore);
    Istruttore cercaPerUsername(String username);
    List<Istruttore> trovaTutti();
    void aggiornaIstruttore(Istruttore istruttore );
}
