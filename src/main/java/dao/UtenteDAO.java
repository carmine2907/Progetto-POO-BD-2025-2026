package dao;

import model.Utente;

public interface UtenteDAO {

    void salva(Utente utente);
    Utente cercaPerUsername(String login);

}