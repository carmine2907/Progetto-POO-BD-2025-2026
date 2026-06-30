package dao;

import model.Partita;

import java.util.List;

public interface PartitaDAO {
    void salva(Partita partita);
    List<Partita> trovaTutti();
}