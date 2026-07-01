package controller;

import dao.AtletaDAO;
import dao.UtenteDAO;
import dao.SquadraDAO;
import dao.PartitaDAO;
import dao.CampoDAO;
import dao.PagamentoDAO;
import implementazionePostgresDAO.AtletaImplementazionePostgresDAO;
import implementazionePostgresDAO.UtenteImplementazionePostgresDao;
import implementazionePostgresDAO.SquadraImplementazionePostgresDAO;
import implementazionePostgresDAO.PartitaImplementazionePostgresDAO;
import implementazionePostgresDAO.CampoImplementazionePostgresDAO;
import implementazionePostgresDAO.PagamentoImplementazionePostgresDAO;
import model.Utente;
import model.Campo;
import model.Partita;
import model.Squadra;
import model.Atleta;

import controller.exceptions.AtletaGiaPresenteException;
import controller.exceptions.PagamentoNonValidoException;
import controller.exceptions.SquadraCompletaException;
import controller.exceptions.UtenteGiaEsistenteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SistemaController {
    private AtletaDAO atletaDAO;
    private UtenteDAO utenteDAO;
    private SquadraDAO squadraDAO;
    private PartitaDAO partitaDAO;
    private CampoDAO campoDAO;
    private PagamentoDAO pagamentoDAO;
    private static Utente utenteLoggato;

    public SistemaController() {
        // Istanziazione delle classi di implementazione Postgres reali
        this.atletaDAO = new AtletaImplementazionePostgresDAO();
        this.utenteDAO = new UtenteImplementazionePostgresDao(); //unico dao scritto male (Dao)
        this.squadraDAO = new SquadraImplementazionePostgresDAO();
        this.partitaDAO = new PartitaImplementazionePostgresDAO();
        this.campoDAO   = new CampoImplementazionePostgresDAO();
        this.pagamentoDAO= new PagamentoImplementazionePostgresDAO();
    }

    /**
     * Esegue il login dell'utente previa verifica credenziali.
     */
    public boolean login(String login, String password) throws Exception {
        if (login == null || login.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username e password non possono essere vuoti.");
        }

        // 1. Pulizia di eventuali spazi invisibili digitati per sbaglio nella GUI
        login = login.trim();
        password = password.trim();

        System.out.println("--- DEBUG LOGIN ---");
        System.out.println("Cerco nel DB l'utente: '" + login + "'");

        // 2. Chiamata al DAO
        Utente utenteTrovato = utenteDAO.cercaPerUsername(login);

        // 3. Verifica se l'utente esiste davvero nel DB
        if (utenteTrovato == null) {
            System.out.println("ERRORE: L'utente '" + login + "' NON ESISTE nel database! Il DAO ha restituito null.");
            return false;
        }

        System.out.println("Utente trovato! La password salvata nel DB è: '" + utenteTrovato.getPassword() + "'");
        System.out.println("La password digitata nella GUI è: '" + password + "'");

        // 4. Verifica se le password coincidono
        if (utenteTrovato.getPassword().equals(password)) {
            utenteLoggato = utenteTrovato;
            System.out.println("Le password coincidono. Login Riuscito!");
            return true;
        } else {
            System.out.println("ERRORE: Le password NON coincidono.");
            return false;
        }
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
                    "Il campo '" + campo.getNome() + "' non è disponibile.");
        }

        // Creazione partita
        Partita nuovaPartita = new Partita(idPartita, data, ora, campo);

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
    public Utente registraUtente(String login,
                                 String password,
                                 String nome,
                                 String cognome,
                                 String codiceRuolo)
            throws Exception {

        // 1. Validazione campi obbligatori
        if (nome == null || nome.trim().isEmpty()
                || cognome == null || cognome.trim().isEmpty()
                || login == null || login.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || codiceRuolo == null || codiceRuolo.trim().isEmpty()) {

            throw new IllegalArgumentException("Tutti i campi, compreso il codice ruolo, sono obbligatori.");
        }

        // 2. Controllo reale sul database se lo username esiste già
        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }

        // 3. Interpretazione del codice identificativo per stabilire il tipo di oggetto
        Utente nuovoUtente;

        switch (codiceRuolo) {
            case "0001":
                // È un Atleta: usiamo valori temporanei di default per data di nascita e ruolo sul campo
                nuovoUtente = new Atleta(login, password, nome, cognome, "2000-01-01", "Da assegnare");
                break;

            case "2224":
                // È un Dirigente
                // NOTA: Se hai creato una classe specifica model.Dirigente usa quella,
                // altrimenti usa Utente base (il DB lo riconoscerà tramite le query di ruolo)
                nuovoUtente = new Utente(nome, cognome, login, password);
                break;

            case "5557":
                // È un Allenatore
                // NOTA: Se hai creato una classe specifica model.Allenatore usa quella
                nuovoUtente = new Utente(nome, cognome, login, password);
                break;

            default:
                // Se il codice digitato non è tra quelli previsti, blocchiamo la registrazione
                throw new IllegalArgumentException("Codice Identificativo Ruolo non valido.\nContatta l'amministratore del sistema.");
        }

        // 4. Salvataggio persistente nel Database
        // Inseriamo prima l'utente nella tabella base 'utente' per generare l'ID (SERIAL)
        utenteDAO.salva(nuovoUtente);

        // 5. Gestione dell'ereditarietà per l'Atleta
        if (nuovoUtente instanceof Atleta) {
            // Recuperiamo l'ID appena generato da PostgreSQL cercando l'utente per username
            Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
            ((Atleta) nuovoUtente).setIdUtente(utenteRegistrato.getIdUtente());

            // Salviamo i dettagli specifici dell'atleta nella tabella 'atleta'
            atletaDAO.salva((Atleta) nuovoUtente);
        }

        return nuovoUtente;
    }


    public List<Squadra> getTutteLeSquadre() {
        // Delega il compito di interrogare il DB al DAO specifico
        return squadraDAO.trovaTutti();
    }
    public List<Partita> getTutteLePartite() {
        return partitaDAO.trovaTutti();
    }
    public List<Campo> getTuttiICampi() {
        return campoDAO.trovaTutti();
    }
    public List<Atleta> getTuttiGliAtleti() {return atletaDAO.trovaTutti();}
    public List<Atleta> getAtletiPerSquadra(int idSquadra) {return atletaDAO.getAtletiPerSquadra(idSquadra);}

    public String verificaStatoPagamento(int idAtleta) {
        return pagamentoDAO.getUltimoStatoPagamento(idAtleta);}
}