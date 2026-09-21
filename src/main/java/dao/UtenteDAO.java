package dao;

import model.Utente;


import java.util.List;


public interface UtenteDAO {
        public void salva(Utente utente);
        Utente cercaPerUsername(String username);
        List<Utente> trovaTutti();
       void aggiornaUtente(Utente utente);
       Utente cercaPerId(String idUtente);
    }

