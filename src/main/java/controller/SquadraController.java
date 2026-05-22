package controller;

import controller.exception.*;
import model.Squadra;
import model.Atleta;

public class SquadraController {

    public SquadraController() {}

    /**
     * Associa un atleta a una squadra verificando i vincoli di business.
     */
    public void assegnaAtletaASquadra(Atleta atleta, Squadra squadra)
            throws UtenteNonAutenticatoException, SquadraPienaException, PagamentoInvalidoException {

        // 1. Controllo di sicurezza: l'operazione richiede un utente loggato (es. un Dirigente o Allenatore)
        if (!AutenticazioneController.isUtenteAutenticato()) {
            throw new UtenteNonAutenticatoException("Operazione negata: effettuare prima il login.");
        }

        // 2. Controllo Vincolo Burocratico: l'atleta ha pagato la quota?
        if (!atleta.isPagamentoInRegola()) {
            throw new PagamentoInvalidoException("Impossibile inserire l'atleta: la quota di iscrizione non risulta saldata.");
        }

        // 3. Controllo Vincolo Capienza: la squadra è completa?
        if (squadra.isCompleta()) {
            throw new SquadraPienaException("La squadra " + squadra.getNome() + " ha già raggiunto il numero massimo di giocatori.");
        }

        // 4. Se tutti i controlli passano, esegue l'operazione nel modello
        boolean successo = squadra.addAtleta(atleta);

        if (successo) {
            // NOTA: Qui andrà la chiamata al DAO per rendere persistente l'associazione sul database PostgreSQL
            // squadraDAO.aggiungiAtletaAStudio(atleta.getLogin(), squadra.getIdSquadra());
        }
    }
}