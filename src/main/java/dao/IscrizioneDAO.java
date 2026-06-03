package dao;

import model.Iscrizione;

public interface IscrizioneDAO {
    void salva(Iscrizione iscrizione);
    Iscrizione cercaPerId(int idIscrizione);
    void aggiornaValidita(int idIscrizione, boolean valida);
}