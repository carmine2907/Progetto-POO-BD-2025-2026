package controller;

import dao.AtletaDAO;
import model.Utente;
import model.Campo;
import model.Partita;
import model.Squadra;
import model.Atleta;

import exceptions.AtletaGiaPresenteException;
import exceptions.PagamentoNonValidoException;
import exceptions.SquadraCompletaException;
import exceptions.UtenteGiaEsistenteException;

import java.time.LocalDate;
import java.time.LocalTime;

public class SistemaController {
    private AtletaDAO atletaDAO;
    // Mantiene la sessione attiva
    private static Utente utenteLoggato;

    public SistemaController() {
    }

    /**
     * Esegue il login dell'utente previa verifica credenziali.
     */
    public boolean login(String username, String password) throws Exception {

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username e password non possono essere vuoti.");
        }

        // Simulazione login
        if ("admin".equals(username)
                && "password".equals(password)) {

            // Simulazione utente loggato
            utenteLoggato = new Utente(
                    "Admin",
                    "Sistema",
                    username,
                    password
            );

            return true;
        }

        return false;
    }

    /**
     * Logout utente.
     */
    public void logout() {
        utenteLoggato = null;
    }

    /**
     * Restituisce l'utente loggato.
     */
    public static Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    /**
     * Verifica autenticazione.
     */
    public static boolean isUtenteAutenticato() {
        return utenteLoggato != null;
    }

    /**
     * Pianifica una nuova partita verificando disponibilità campo.
     */
    public Partita pianificaPartita(int idPartita,
                                    LocalDate data,
                                    LocalTime ora,
                                    Campo campo)
            throws Exception {

        if (!isUtenteAutenticato()) {

            throw new Exception(
                    "Operazione consentita solo ad utenti autenticati.");
        }

        // Controllo disponibilità campo
        if (!campo.isDisponibile()) {

            throw new IllegalStateException(
                    "Il campo '" + campo.getNome()
                            + "' non è disponibile.");
        }

        // Creazione partita
        Partita nuovaPartita =
                new Partita(idPartita, data, ora, campo);

        // Campo occupato
        campo.setDisponibile(false);

        // futura chiamata DAO

        return nuovaPartita;
    }

    /**
     * Associa atleta a squadra.
     */
    public void assegnaAtletaASquadra(Atleta atleta,
                                      Squadra squadra)
            throws Exception,
            SquadraCompletaException,
            PagamentoNonValidoException,
            AtletaGiaPresenteException {

        // Controllo login
        if (!isUtenteAutenticato()) {

            throw new Exception(
                    "Effettuare il login prima dell'operazione.");
        }

        // Controllo pagamento
        if (!atleta.isPagamentoInRegola()) {

            throw new PagamentoNonValidoException(
                    "Pagamento non effettuato.");
        }

        // Controllo squadra piena
        if (squadra.isCompleta()) {

            throw new SquadraCompletaException(
                    "La squadra " + squadra.getNome()
                            + " è completa.");
        }

        // Controllo atleta già presente
        if (squadra.getAtleti().contains(atleta)) {

            throw new AtletaGiaPresenteException(
                    "Atleta già presente nella squadra.");
        }

        // Inserimento atleta
        boolean successo = squadra.addAtleta(atleta);

        if (successo) {

            // futura chiamata DAO
            // squadraDAO.save(...)
        }
    }

    /**
     * Registrazione nuovo utente.
     */
    public Utente registraUtente(String nome,
                                 String cognome,
                                 String username,
                                 String password)
            throws IllegalArgumentException,
            UtenteGiaEsistenteException {

        // Validazione campi
        if (nome == null || nome.trim().isEmpty()
                || cognome == null || cognome.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Tutti i campi sono obbligatori.");
        }

        // Controllo password
        if (password.length() < 6) {

            throw new IllegalArgumentException(
                    "La password deve contenere almeno 6 caratteri.");
        }

        // Simulazione username già esistente
        if (username.equalsIgnoreCase("admin")) {

            throw new UtenteGiaEsistenteException(
                    "Username già esistente.");
        }

        // Creazione utente
        Utente nuovoUtente = new Utente(nome, cognome, username, password);

        // futura chiamata DAO

        return nuovoUtente;
    }
}

