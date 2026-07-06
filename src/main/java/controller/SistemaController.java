package controller;

import dao.*;

import implementazionePostgresDAO.*;
import model.*;

import controller.Exceptions.AtletaGiaPresenteException;
import controller.Exceptions.PagamentoNonValidoException;
import controller.Exceptions.SquadraCompletaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The type Sistema controller.
 */
public class SistemaController {
    private AtletaDAO atletaDAO;
    private UtenteDAO utenteDAO;
    private SquadraDAO squadraDAO;
    private PartitaDAO partitaDAO;
    private CampoDAO campoDAO;
    private PagamentoDAO pagamentoDAO;
    private DirigenteDAO dirigenteDAO;
    private AllenatoreDAO allenatoreDAO;
    private static Utente utenteLoggato;

    /**
     * Instantiates a new Sistema controller.
     */
    public SistemaController() {
        // Istanziazione delle classi di implementazione Postgres reali
        this.atletaDAO = new AtletaImplementazionePostgresDAO();
        this.utenteDAO = new UtenteImplementazionePostgresDao(); //unico dao scritto male (Dao)
        this.squadraDAO = new SquadraImplementazionePostgresDAO();
        this.partitaDAO = new PartitaImplementazionePostgresDAO();
        this.campoDAO   = new CampoImplementazionePostgresDAO();
        this.pagamentoDAO= new PagamentoImplementazionePostgresDAO();
        this.dirigenteDAO = new DirigenteImplementazionePostgresDAO();
        this.allenatoreDAO = new AllenatoreImplementazionePostgresDAO();
    }

    /**
     * Esegue il login dell'utente previa verifica credenziali.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws Exception the exception
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

    /**
     * Logout.
     */
    public void logout() {
        utenteLoggato = null;
    }


     //Restituisce l'utente loggato.

    /**
     * Gets utente loggato.
     *
     * @return the utente loggato
     */
    public static Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    /**
     * Verifica autenticazione.
     *
     * @return the boolean
     */
    public static boolean isUtenteAutenticato() {
        return utenteLoggato != null;
    }

    /**
     * Pianifica una nuova partita verificando disponibilità campo.
     *
     * @param idPartita the id partita
     * @param data      the data
     * @param ora       the ora
     * @param campo     the campo
     * @return the partita
     * @throws Exception the exception
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
     *
     * @param atleta  the atleta
     * @param squadra the squadra
     * @throws Exception                   the exception
     * @throws SquadraCompletaException    the squadra completa exception
     * @throws PagamentoNonValidoException the pagamento non valido exception
     * @throws AtletaGiaPresenteException  the atleta gia presente exception
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
     * Registra atleta atleta.
     *
     * @param login       the login
     * @param password    the password
     * @param nome        the nome
     * @param cognome     the cognome
     * @param dataNascita the data nascita
     * @param ruolo       the ruolo
     * @return the atleta
     * @throws Exception the exception
     */
    public Atleta registraAtleta(String login, String password, String nome, String cognome, String dataNascita, String ruolo) throws Exception {

        // 1. Validazione campi obbligatori
        if (nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty() ||
                dataNascita == null || dataNascita.isEmpty() || ruolo == null) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }

        // 2. Controllo duplicati
        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }

        // 3. Creazione oggetto Atleta
        Atleta nuovoAtleta = new Atleta(login, password, nome, cognome, dataNascita, ruolo);

        // Di default, appena registrato, il pagamento non è in regola
        nuovoAtleta.setPagamentoInRegola(false);

        // 4. Salvataggio in DB: prima l'utente base (per ottenere l'ID)
        utenteDAO.salva(nuovoAtleta);

        // 5. Recupero l'ID generato dal database e lo assegno all'atleta
        Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
        nuovoAtleta.setIdUtente(utenteRegistrato.getIdUtente());

        // 6. Salvataggio finale nella tabella specifica "atleta"
        atletaDAO.salva(nuovoAtleta);

        return nuovoAtleta;
    }

    /**
     * Registrazione specifica per un nuovo Allenatore
     *
     * @param login     the login
     * @param password  the password
     * @param nome      the nome
     * @param cognome   the cognome
     * @param qualifica the qualifica
     * @return the allenatore
     * @throws Exception the exception
     */
    public Allenatore registraAllenatore(String login, String password, String nome, String cognome, String qualifica) throws Exception {

        // 1. Validazione campi obbligatori
        if (nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty() ||
                qualifica == null || qualifica.isEmpty()) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }

        // 2. Controllo duplicati
        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }

        // 3. Creazione oggetto Allenatore
        Allenatore nuovoAllenatore = new Allenatore(login, password, nome, cognome, qualifica);

        // 4. Salvataggio in DB: prima l'utente base (per ottenere l'ID generato col SERIAL)
        utenteDAO.salva(nuovoAllenatore);

        // 5. Recupero l'ID generato dal database e lo assegno all'allenatore
        Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
        nuovoAllenatore.setIdUtente(utenteRegistrato.getIdUtente());

        // 6. Salvataggio finale nella tabella specifica (assicurati di avere questo DAO istanziato in alto!)
        allenatoreDAO.salva(nuovoAllenatore);

        return nuovoAllenatore;
    }

    /**
     * Registrazione specifica per un nuovo Dirigente
     *
     * @param login              the login
     * @param password           the password
     * @param nome               the nome
     * @param cognome            the cognome
     * @param ruoloOrganizzativo the ruolo organizzativo
     * @return the dirigente
     * @throws Exception the exception
     */
    public Dirigente registraDirigente(String login, String password, String nome, String cognome, String ruoloOrganizzativo) throws Exception {

        // 1. Validazione campi obbligatori
        if (nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty() ||
                ruoloOrganizzativo == null || ruoloOrganizzativo.isEmpty()) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }

        // 2. Controllo duplicati (basato sullo username univoco ereditato da Utente)
        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }

        // 3. Creazione oggetto Dirigente
        Dirigente nuovoDirigente = new Dirigente(login, password, nome, cognome, ruoloOrganizzativo);

        // 4. Salvataggio in DB: prima l'utente base (per generare l'ID)
        utenteDAO.salva(nuovoDirigente);

        // 5. Recupero l'ID appena generato dal database e lo assegno al dirigente
        Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
        nuovoDirigente.setIdUtente(utenteRegistrato.getIdUtente());

        // 6. Salvataggio finale nella tabella specifica (se hai implementato il relativo DAO)
        dirigenteDAO.salva(nuovoDirigente);

        return nuovoDirigente;
    }

    /**
     * Gets tutte le squadre.
     *
     * @return the tutte le squadre
     */
    public List<Squadra> getTutteLeSquadre() { return squadraDAO.trovaTutti(); }

    /**
     * Gets tutte le partite.
     *
     * @return the tutte le partite
     */
    public List<Partita> getTutteLePartite() {
        return partitaDAO.trovaTutti();
    }

    /**
     * Gets tutti i campi.
     *
     * @return the tutti i campi
     */
    public List<Campo> getTuttiICampi() {
        return campoDAO.trovaTutti();
    }

    /**
     * Gets tutti gli atleti.
     *
     * @return the tutti gli atleti
     */
    public List<Atleta> getTuttiGliAtleti() { return atletaDAO.trovaTutti(); }

    /**
     * Gets atleti per squadra.
     *
     * @param idSquadra the id squadra
     * @return the atleti per squadra
     */
    public List<Atleta> getAtletiPerSquadra(int idSquadra) { return atletaDAO.getAtletiPerSquadra(idSquadra); }

    /**
     * Verifica stato pagamento string.
     *
     * @param idAtleta the id atleta
     * @return the string
     */
    public String verificaStatoPagamento(int idAtleta) {
        return pagamentoDAO.getUltimoStatoPagamento(idAtleta);}
}