package dao;

import model.Iscritto;


import java.util.List;

public interface IscrittoDAO {
    public void salva(Iscritto iscritto);
    Iscritto cercaPerUsername(String username);
    List<Iscritto> trovaTutti();
    void aggiornaIscritto(Iscritto iscritto);
}
