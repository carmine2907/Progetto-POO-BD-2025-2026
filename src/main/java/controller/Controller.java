package controller;
import controller.Exception.CorsoGiaEsistenteException;
import controller.Exception.SchedaGiaEsistenteException;
import dao.*;
import implementazionePostgresDAO.*;
import model.*;
import controller.Exception.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Controller.
 */
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

    /**
     * Instantiates a new Controller.
     */
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

    /**
     * Accedi boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean accedi(String username, String password) throws Exception {
        Utente u = utenteDAO.cercaPerUsername(username);

        if (u == null) {

            throw new Exception("Credenziali errate!");
        }

        if (!u.getPassword().trim().equals(password.trim())) {

            throw new Exception("Credenziali errate!");
        }

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

        throw new RuntimeException("Credenziali errate!");
    }

    /**
     * Logout.
     */
    public void logout() {
        this.utenteLoggato = null;
    }

    /**
     * Gets utente loggato.
     *
     * @return the utente loggato
     */
    public Utente getUtenteLoggato() {
        return this.utenteLoggato;
    }

    /**
     * Crea corso corso.
     *
     * @param id_Corso          the id corso
     * @param nomeCorso         the nome corso
     * @param capienza          the capienza
     * @param istruttoreGestore the istruttore gestore
     * @param partecipazione    the partecipazione
     * @return the corso
     * @throws CorsoGiaEsistenteException the corso gia esistente exception
     */
    public Corso CreaCorso  (String id_Corso, String nomeCorso, int capienza, Istruttore istruttoreGestore, ArrayList<Partecipa> partecipazione) throws CorsoGiaEsistenteException{

        if(corsoDAO.cercaPerNomeCorso(nomeCorso)!=null || corsoDAO.cercaPerId_Corso(id_Corso)!=null){

            throw new CorsoGiaEsistenteException("Corso già esistente ");
        }
        Corso corsoCreato = new Corso(id_Corso,nomeCorso,capienza, istruttoreGestore,partecipazione) ;
        corsoDAO.salva(corsoCreato);
        return corsoCreato;
    }

    /**
     * Crea scheda scheda allenamento.
     *
     * @param id_scheda           the id scheda
     * @param descrizione         the descrizione
     * @param istruttoreCreatore  the istruttore creatore
     * @param iscrittoPropietario the iscritto propietario
     * @param id_iscritto         the id iscritto
     * @return the scheda allenamento
     * @throws Exception the exception
     */
    public SchedaAllenamento CreaScheda(String id_scheda,String descrizione ,Istruttore istruttoreCreatore,Iscritto iscrittoPropietario,String id_iscritto)throws Exception{
       if(schedaAllenamentoDAO.cercaPerId_Scheda(id_scheda)!=null && schedaAllenamentoDAO.cercaPerid_Iscritto(id_iscritto)!=null){

           throw new SchedaGiaEsistenteException("scheda già esistente!!! ");
       }

        SchedaAllenamento schedaCreata= new SchedaAllenamento(id_scheda,descrizione,istruttoreCreatore,iscrittoPropietario);
        schedaAllenamentoDAO.salva(schedaCreata);
        return schedaCreata;
    }

    /**
     * Assegna scheda.
     *
     * @param id_scheda           the id scheda
     * @param descrizione         the descrizione
     * @param istruttoreCreatore  the istruttore creatore
     * @param iscrittoPropietario the iscritto propietario
     * @param id_iscritto         the id iscritto
     * @throws Exception the exception
     */
    public void AssegnaScheda(String id_scheda, String descrizione, Istruttore istruttoreCreatore, Iscritto iscrittoPropietario, String id_iscritto) throws Exception {
        SchedaAllenamentoDAO schedaDAO = new SchedaAllenamentoImplementazionePostgresDAO();


        SchedaAllenamento schedaEsistente = schedaDAO.cercaPerid_Iscritto(id_iscritto);

        if (schedaEsistente != null) {
            throw new SchedaGiaAssegnataException("scheda gia assegnata !");
        }

        SchedaAllenamento nuovaScheda = new SchedaAllenamento(id_scheda, descrizione, istruttoreCreatore, iscrittoPropietario);
        schedaDAO.salva(nuovaScheda);
    }

    /**
     * Is corso al completo boolean.
     *
     * @param corso the corso
     * @return the boolean
     */
    public boolean isCorsoAlCompleto(Corso corso ) {
        PartecipaDAO partecipaDAO = new PartecipaImplementazionePostgresDAO();


        int iscrittiAttuali = corsoDAO.contaIscrittiAlCorso(corso.getId_Corso());

        // 2. Confronto con la capienza massima del corso
        return iscrittiAttuali >= corso.getCapienza();
    }

    /**
     * Visualizza scheda personale scheda allenamento.
     *
     * @param utenteLoggato the utente loggato
     * @return the scheda allenamento
     */
    public SchedaAllenamento visualizzaSchedaPersonale(Iscritto utenteLoggato) {

        SchedaAllenamentoDAO schedaDAO = new implementazionePostgresDAO.SchedaAllenamentoImplementazionePostgresDAO();
        return schedaDAO.cercaPerid_Iscritto(utenteLoggato.getId_utente());
    }

    /**
     * Prenota corso boolean.
     *
     * @param iscritto the iscritto
     * @param corso    the corso
     * @return the boolean
     * @throws CorsoGiaEsistenteException the corso gia esistente exception
     * @throws CorsoGiaPrenotatoException the corso gia prenotato exception
     * @throws CorsoAlCompletoException   the corso al completo exception
     */
    public boolean prenotaCorso(Iscritto iscritto, Corso corso) throws CorsoGiaEsistenteException,CorsoGiaPrenotatoException,CorsoAlCompletoException{


        if (isCorsoAlCompleto(corso)) {
            throw new CorsoAlCompletoException("il corso ha raggiunto la capienza massima !!");
        }

        //  Controllo se l'iscritto ha già prenotato questo corso E se l'ID del corso nella partecipazione è uguale a quello del corso selezionato
        if (iscritto.getPartecipazioni() != null) {
            for (Partecipa partecipazione : iscritto.getPartecipazioni()) {

                if (partecipazione.getId_Corso().equals(corso.getId_Corso())) {
                    throw new CorsoGiaPrenotatoException("Hai già effettuato la prenotazione per il corso: " + corso.getNomeCorso());
                }
            }
        }

        Partecipa nuovaPartecipazione = new Partecipa(corso.getId_Corso(), iscritto.getId_utente(), corso, iscritto);
        PartecipaImplementazionePostgresDAO partecipaDAO = new PartecipaImplementazionePostgresDAO();
        partecipaDAO.salva(nuovaPartecipazione);

        if(iscritto.getPartecipazioni() != null){
            iscritto.getPartecipazioni().add(nuovaPartecipazione);
        }

        return true;
    }

    /**
     * Stampa ricevuta string.
     *
     * @param ricevutapag the ricevutapag
     * @return the string
     */
    public String StampaRicevuta(Pagamento ricevutapag){

        return "pagamento riuscito \n  "+"idpagamento: "+ricevutapag.getId_Pagamento()+"\n euro: "+ricevutapag.getImporto();
    }

    /**
     * Paga abbonamento pagamento.
     *
     * @param id_pagamento the id pagamento
     * @return the pagamento
     * @throws PagamentoGiaEffettuatoException the pagamento gia effettuato exception
     */
    public Pagamento pagaAbbonamento( String id_pagamento) throws PagamentoGiaEffettuatoException {




       if (utenteLoggato instanceof Iscritto) {
            Iscritto iscritto = (Iscritto) utenteLoggato;
            if (iscritto.getPagamenti() != null && !iscritto.getPagamenti().isEmpty()) {
                throw new PagamentoGiaEffettuatoException("Hai già effettuato il pagamento del tuo abbonamento!");
            }
        }

        double importoCorretto;


        if (utenteLoggato instanceof MembroVip) {
            importoCorretto = 70.0;
            System.out.println("Mario Doccia");
        } else {
            importoCorretto = 50.0;
            System.out.println("Dario Moccia");
        }
         if (utenteDAO.cercaPerId(((Iscritto) utenteLoggato).getId_utente()) == null) {
             iscrittoDAO.salva((Iscritto) utenteLoggato);
         }


        Pagamento pagamento = new Pagamento(id_pagamento, importoCorretto, (Iscritto) utenteLoggato);


        pagamentoDAO.salva(pagamento);


        if (utenteLoggato instanceof Iscritto) {
            ((Iscritto) utenteLoggato).getPagamenti().add(pagamento);
        }

        return pagamento;
    }


    /**
     * Accesso area wellness boolean.
     *
     * @return the boolean
     * @throws AccessoAreaWellnessException the accesso area wellness exception
     */
    public boolean AccessoAreaWellness() throws AccessoAreaWellnessException {

        if (this.utenteLoggato instanceof MembroVip) {
            return true;
        } else {
            throw new AccessoAreaWellnessException("Accesso Negato: L'Area  è riservata esclusivamente ai Membri VIP. ");
        }
    }

    /**
     * Get info servizi servizio wellness.
     *
     * @param servizioWellness the servizio wellness
     * @return the servizio wellness
     */
    public ServizioWellness GetInfoServizi(ServizioWellness servizioWellness){
  servizioWellness.getId_ServizioWellness();
     servizioWellness.getNomeServizio();
          servizioWellness.getDisponibile();

          return servizioWellness;
    }


    /**
     * Prenota servizi boolean.
     *
     * @param utenteLoggato      the utente loggato
     * @param serviziSelezionati the servizi selezionati
     * @return the boolean
     */
    public boolean prenotaServizi(MembroVip utenteLoggato, List<ServizioWellness> serviziSelezionati) {
            ServizioWellnessDAO servizioDAO = new implementazionePostgresDAO.ServizioWellnessImplementazionePostgresDAO();

            for (ServizioWellness servizio : serviziSelezionati) {

                servizio.setDisponibile(false);
                servizio.setMembroPrenotante(utenteLoggato);
                servizioDAO.aggiornaServizioWellness(servizio);


                if (utenteLoggato.getServiziPrenotati() != null) {
                    utenteLoggato.getServiziPrenotati().add(servizio); //aggiungere il servizio all'utente
                }
            }
            return true;
        }

    /**
     * Ottieni tutti i corsi list.
     *
     * @return the list
     */
    public List<Corso> ottieniTuttiICorsi() {
        return corsoDAO.trovaTutti();}

    /**
     * Ottieni tutti gli iscritti list.
     *
     * @return the list
     */
    public List<Iscritto> ottieniTuttiGliIscritti() {
        return iscrittoDAO.trovaTutti();
    }
    }

