package controller;
import dao.*;
import implementazionePostgresDAO.*;
import model.*;

import java.util.ArrayList;
import java.util.List;


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

    public Corso CreaCorso  (String id_Corso, String nomeCorso, int capienza, Istruttore istruttoreGestore, ArrayList<Partecipa> partecipazione) throws Exception{
         capienza=0;
        if(corsoDAO.cercaPerNomeCorso(nomeCorso)!=null && corsoDAO.cercaPerId_Corso(id_Corso)!=null){
          //checked exception da fare
            throw new Exception("Corso già esistente ");
        }
        Corso corsoCreato = new Corso(id_Corso,nomeCorso,capienza, istruttoreGestore,partecipazione) ;
        corsoDAO.salva(corsoCreato);
        return corsoCreato;
    }
    public SchedaAllenamento CreaScheda(String id_scheda,String descrizione ,Istruttore istruttoreCreatore,Iscritto iscrittoPropietario,String id_iscritto)throws Exception{
       if(schedaAllenamentoDAO.cercaPerId_Scheda(id_scheda)!=null && schedaAllenamentoDAO.cercaPerid_Iscritto(id_iscritto)!=null){
          // checked
           throw new Exception("scheda già esistente!!! ");
       }

        SchedaAllenamento schedaCreata= new SchedaAllenamento(id_scheda,descrizione,istruttoreCreatore,iscrittoPropietario);
        schedaAllenamentoDAO.salva(schedaCreata);
        return schedaCreata;
    }

    public void AssegnaScheda(String id_scheda,String descrizione ,Istruttore istruttoreCreatore,Iscritto iscrittoPropietario,String id_iscritto) throws Exception{
        //creare una exception che sia in grado di controllare se l'utente abbia premuto il bottone di pagamento
        //if()
        //creare un exception per quando un utente ha già una scheda  già assegnata
  //     if()
        // 1. Crea la nuova istanza della scheda
        SchedaAllenamento nuovaScheda = new SchedaAllenamento(id_scheda, descrizione, istruttoreCreatore, iscrittoPropietario);

        // 2. Aggiorna le liste degli oggetti in memoria
        istruttoreCreatore.getSchedeCreate().add(nuovaScheda);
        iscrittoPropietario.setSchedaAllenamento(nuovaScheda);

        // 3. Salva la scheda nel database richiamando il DAO esistente
        SchedaAllenamentoDAO schedaDAO = new implementazionePostgresDAO.SchedaAllenamentoImplementazionePostgresDAO();
        schedaDAO.salva(nuovaScheda);

    }

    public Boolean haCapienzaMassima(int capienza ,int capienzaMax){
        if(capienza<=capienzaMax){
            return false;
        }
        else{
            return true;
        }
    }
}
