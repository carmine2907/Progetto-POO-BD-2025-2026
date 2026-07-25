package controller;

import dao.*;

import implementazionePostgresDAO.*;
import model.*;

import controller.Exceptions.AtletaGiaPresenteException;
import controller.Exceptions.PagamentoNonValidoException;
import controller.Exceptions.SquadraCompletaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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


        login = login.trim();
        password = password.trim();

        System.out.println("--- DEBUG LOGIN ---");
        System.out.println("Cerco nel DB l'utente: '" + login + "'");


        Utente utenteTrovato = utenteDAO.cercaPerUsername(login);

        if (utenteTrovato == null) {
            System.out.println("ERRORE: L'utente '" + login + "' NON ESISTE nel database! Il DAO ha restituito null.");
            return false;
        }

        System.out.println("Utente trovato! La password salvata nel DB è: '" + utenteTrovato.getPassword() + "'");
        System.out.println("La password digitata nella GUI è: '" + password + "'");

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
    public Partita pianificaPartita(int idPartita, LocalDate data, LocalTime ora, Campo campo)
            throws Exception {

        if (!isUtenteAutenticato()) {

            throw new Exception("Operazione consentita solo ad utenti autenticati.");
        }

        if (!campo.isDisponibile()) {

            throw new IllegalStateException(
                    "Il campo '" + campo.getNome() + "' non è disponibile.");
        }


        Partita nuovaPartita = new Partita(idPartita, data, ora, campo);

        campo.setDisponibile(false);

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
    public void assegnaAtletaASquadra(Atleta atleta, Squadra squadra)
            throws Exception,
            SquadraCompletaException,
            PagamentoNonValidoException,
            AtletaGiaPresenteException {

        if (!isUtenteAutenticato()) {

            throw new Exception("Effettuare il login prima dell'operazione.");
        }


        if (!atleta.isPagamentoInRegola()) {

            throw new PagamentoNonValidoException("Pagamento non effettuato.");
        }


        if (squadra.isCompleta()) {

            throw new SquadraCompletaException("La squadra " + squadra.getNome() + " è completa.");
        }

        if (squadra.getAtleti().contains(atleta)) {

            throw new AtletaGiaPresenteException("Atleta già presente nella squadra.");
        }


        boolean successo = squadra.addAtleta(atleta);

        if (successo) {

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

        if (nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty() ||
                dataNascita == null || dataNascita.isEmpty() || ruolo == null) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }


        try {
            // Tenta di fare il parsing nel formato standard YYYY-MM-DD
            LocalDate.parse(dataNascita);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La data di nascita deve essere nel formato AAAA-MM-GG (es. 2006-07-29).");
        }


        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }


        Atleta nuovoAtleta = new Atleta(login, password, nome, cognome, dataNascita, ruolo);
        nuovoAtleta.setPagamentoInRegola(false);

        utenteDAO.salva(nuovoAtleta);

        Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
        nuovoAtleta.setIdUtente(utenteRegistrato.getIdUtente());

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


        if (nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty() ||
                qualifica == null || qualifica.isEmpty()) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }


        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }


        Allenatore nuovoAllenatore = new Allenatore(login, password, nome, cognome, qualifica);


        utenteDAO.salva(nuovoAllenatore);


        Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
        nuovoAllenatore.setIdUtente(utenteRegistrato.getIdUtente());


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


        if (nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty() ||
                ruoloOrganizzativo == null || ruoloOrganizzativo.isEmpty()) {
            throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
        }


        if (utenteDAO.cercaPerUsername(login) != null) {
            throw new Exception("Username già esistente.");
        }


        Dirigente nuovoDirigente = new Dirigente(login, password, nome, cognome, ruoloOrganizzativo);


        utenteDAO.salva(nuovoDirigente);


        Utente utenteRegistrato = utenteDAO.cercaPerUsername(login);
        nuovoDirigente.setIdUtente(utenteRegistrato.getIdUtente());


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