package controller;

import model.Utente;
import model.Campo;
import model.Partita;
import model.Squadra;
import model.Atleta;

import java.time.LocalDate;
import java.time.LocalTime;

public class SistemaController {


    private static Utente utenteLoggato; // Mantiene la sessione attiva

    public SistemaController() {}


    /**
     * Esegue il login dell'utente previa verifica delle credenziali.
     */
    public boolean login(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username e password non possono essere vuoti.");
        }

        // NOTA: Qui inseriremo la chiamata al DAO. Per ora simuliamo un controllo basilare.
        if ("admin".equals(username) && "password".equals(password)) {
            // utenteLoggato = utenteDAO.findByLogin(username);
            return true;
        }

        return false;
    }

    public void logout() {
        utenteLoggato = null;
    }

    public static Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public static boolean isUtenteAutenticato() {
        return utenteLoggato != null;
    }



    /**
     * Serve a pianificare una nuova partita verificando la disponibilità del campo.
     */
    public Partita pianificaPartita(int idPartita, LocalDate data, LocalTime ora, Campo campo)
            throws UtenteNonAutenticatoException, IllegalStateException {

        if (!isUtenteAutenticato()) {
            throw new UtenteNonAutenticatoException("Operazione consentita solo al personale autorizzato.");
        }

        // Controllo della regola di business sul campo
        if (!campo.isDisponibile()) {
            throw new IllegalStateException("Il campo '" + campo.getNome() + "' non è disponibile per la data e l'orario selezionati.");
        }

        // Creazione dell'entità partita
        Partita nuovaPartita = new Partita(idPartita, data, ora, campo);

        // Il campo adesso viene contrassegnato come occupato
        campo.setDisponibile(false);

        // NOTA: Qui inseriremo il salvataggio su DB tramite partitaDAO e campoDAO

        return nuovaPartita;
    }



    /**
     * Associa un atleta a una squadra verificando i vincoli di business.
     */
    public void assegnaAtletaASquadra(Atleta atleta, Squadra squadra)
            throws UtenteNonAutenticatoException, SquadraPienaException, PagamentoInvalidoException {

        // 1. Controllo di sicurezza: l'operazione richiede un utente loggato
        if (!isUtenteAutenticato()) {
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
            // Qui andrà la chiamata al DAO per rendere persistente l'associazione sul database PostgreSQL
            // squadraDAO.aggiungiAtletaAStudio(atleta.getLogin(), squadra.getIdSquadra());
        }
    }

    

    /**
     * Segnalata se un utente prova a fare azioni protette senza essersi autenticato.
     */
    public static class UtenteNonAutenticatoException extends Exception {
        public UtenteNonAutenticatoException(String messaggio) {
            super(messaggio);
        }
    }

    /**
     * Segnalata se si prova ad aggiungere un atleta a una squadra che ha raggiunto il limite max.
     */
    public static class SquadraPienaException extends Exception {
        public SquadraPienaException(String messaggio) {
            super(messaggio);
        }
    }

    /**
     * Segnalata se un atleta tenta di iscriversi o partecipare senza pagamenti in regola.
     */
    public static class PagamentoInvalidoException extends Exception {
        public PagamentoInvalidoException(String messaggio) {
            super(messaggio);
        }
    }
}