package controller;

import dao.AtletaDAO;
import dao.UtenteDAO;
import dao.SquadraDAO;
import dao.PartitaDAO;

import implementazionePostgresDAO.AtletaImplementazionePostgresDAO;
import implementazionePostgresDAO.UtenteImplementazionePostgresDao;
import implementazionePostgresDAO.SquadraImplementazionePostgresDAO;
import implementazionePostgresDAO.PartitaImplementazionePostgresDAO;

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
    private UtenteDAO utenteDAO;
    private SquadraDAO squadraDAO;
    private PartitaDAO partitaDAO;

    // Mantiene la sessione attiva
    private static Utente utenteLoggato;

    public SistemaController() {
        // Istanziazione delle classi di implementazione Postgres reali
        this.atletaDAO = new AtletaImplementazionePostgresDAO();
        this.utenteDAO = new UtenteImplementazionePostgresDao(); //unico dao scritto male (Dao)
        this.squadraDAO = new SquadraImplementazionePostgresDAO();
        this.partitaDAO = new PartitaImplementazionePostgresDAO();
    }

    /**
     * Esegue il login dell'utente previa verifica credenziali.
     */
    public boolean login(String login, String password) throws Exception {

        if (login == null || login.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Username e password non possono essere vuoti.");
        }

        // Recupero l'utente dal database tramite il DAO di Postgres
        Utente utenteTrovato = utenteDAO.cercaPerUsername(login);

        // Controllo se l'utente esiste e se la password corrisponde
        if (utenteTrovato != null && utenteTrovato.getPassword().equals(password)) {
            utenteLoggato = utenteTrovato;
            return true;
        }

        return false;
    }


     //Logout utente.

    public void logout() {
        utenteLoggato = null;
    }


     //Restituisce l'utente loggato.

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

        // Salvataggio persistente nel database della partita pianificata
        partitaDAO.salva(nuovaPartita);

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

            throw new Exception("Effettuare il login prima dell'operazione.");
        }

        // Controllo pagamento
        if (!atleta.isPagamentoInRegola()) {

            throw new PagamentoNonValidoException("Pagamento non effettuato.");
        }

        // Controllo squadra piena
        if (squadra.isCompleta()) {

            throw new SquadraCompletaException("La squadra " + squadra.getNome() + " è completa.");
        }

        // Controllo atleta già presente
        if (squadra.getAtleti().contains(atleta)) {

            throw new AtletaGiaPresenteException("Atleta già presente nella squadra.");
        }

        // Inserimento atleta in memoria
        boolean successo = squadra.addAtleta(atleta);

        if (successo) {
            // Chiamata al DAO per aggiornare il database con il legame corretto
            squadraDAO.assegnaAtleta(squadra, atleta);
        }
    }

    /**
     * Registrazione nuovo utente.
     */
    public Utente registraUtente(String nome,
                                 String cognome,
                                 String login,
                                 String password)
            throws Exception,
            IllegalArgumentException,
            UtenteGiaEsistenteException {

        // Validazione campi
        if (nome == null || nome.trim().isEmpty()
                || cognome == null || cognome.trim().isEmpty()
                || login == null || login.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Tutti i campi sono obbligatori.");}

        // Controllo password
        if (password.length() < 6) {

            throw new IllegalArgumentException("La password deve contenere almeno 6 caratteri.");
        }

        // Controllo reale sul database se lo username esiste già
        if (utenteDAO.cercaPerUsername(login) != null) {

            throw new UtenteGiaEsistenteException(
                    "Username già esistente.");
        }

        // Creazione utente
        Utente nuovoUtente = new Utente(nome, cognome, login, password);

        // Salvataggio definitivo dell'utente registrato
        utenteDAO.salva(nuovoUtente);

        return nuovoUtente;
    }
}