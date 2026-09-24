package controller;
import dao.*;
import implementazionePostgresDAO.*;
import model.*;


public class Controller {
    private UtenteDAO utenteDAO;
    private IscrittoDAO iscrittoDAO;
    private MembroVipDAO membroVipDAO;
    private CorsoDAO corsoDAO;
    private PagamentoDAO pagamentoDAO;
    private SchedaAllenamentoDAO schedaAllenamentoDAO;
    private ServizioWellnessDAO servizioWellnessDAO;
    private IstruttoreDAO istruttoreDAO;
    private PartecipaDAO partecipaDAO;
    private Utente utenteLoggato;

    public Controller() {
        this.utenteDAO = new UtenteImplementazionePostgresDAO();
        this.servizioWellnessDAO = new ServizioWellnessImplementazionePostgresDAO();
        this.schedaAllenamentoDAO = new SchedaAllenamentoImplementazionePostgresDAO();
        this.partecipaDAO =  new PartecipaImplementazionePostgresDAO();
        this.pagamentoDAO = new PagamentoImplementazionePostgresDAO();
        this.membroVipDAO = new MembroVipImplementazionePostgresDAO();
        this.istruttoreDAO = new IstruttoreImplementazionePostgresDAO();
        this.iscrittoDAO = new IscrittoImplementazionePostgresDAO();
        this.corsoDAO = new CorsoImplementazionePostgresDAO();

        this.utenteLoggato = null;
    }

    public boolean accedi(String username, String password) throws Exception {
        Utente u = utenteDAO.cercaPerUsername(username);

        if (u != null && u.getPassword().equals(password)) {
            // Per avere l'oggetto specializzato, potresti dover interrogare i DAO specifici
            // In un'app reale si verifica in quale tabella figlia si trova l'utente
            Istruttore istruttore = istruttoreDAO.cercaPerUsername(username);
            if(istruttore != null) {
                this.utenteLoggato = istruttore;
                return true;
            }

            MembroVip vip = membroVipDAO.cercaPerUsername(username);
            if(vip != null) {
                this.utenteLoggato = vip;
                return true;
            }

            Iscritto iscritto = iscrittoDAO.cercaPerUsername(username);
            if(iscritto != null) {
                this.utenteLoggato = iscritto;
                return true;
            }
        }
        throw new Exception("Credenziali errate!");
    }

    public void logout() {
        this.utenteLoggato = null;
    }

    public Utente getUtenteLoggato() {
        return this.utenteLoggato;
    }

}
