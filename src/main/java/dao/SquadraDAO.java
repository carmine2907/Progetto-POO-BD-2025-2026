package dao;



import model.Squadra;
import model.Atleta;

public interface SquadraDAO {
    // Aggiorna il legame tra un atleta e una squadra
    void assegnaAtleta(Squadra squadra, Atleta atleta) throws Exception;
}