package model;

import java.util.List;

/**
 * The type Corso.
 */
public class Corso {
    private String id_Corso;
    private String nomeCorso;
    private int capienza;

    private Istruttore istruttoreGestore;
    private List<Partecipa> partecipazioni;


    /**
     * Instantiates a new Corso.
     *
     * @param id_Corso          the id corso
     * @param nomeCorso         the nome corso
     * @param capienza          the capienza
     * @param istruttoreGestore the istruttore gestore
     * @param partecipazioni    the partecipazioni
     */
    public Corso(String id_Corso, String nomeCorso, int capienza, Istruttore istruttoreGestore, List<Partecipa> partecipazioni) {
        this.id_Corso = id_Corso;
        this.nomeCorso = nomeCorso;
        this.capienza = capienza;
        this.istruttoreGestore = istruttoreGestore;
        this.partecipazioni = partecipazioni;
    }

    /**
     * Gets id corso.
     *
     * @return the id corso
     */
    public String getId_Corso() { return id_Corso; }

    /**
     * Sets id corso.
     *
     * @param id_Corso the id corso
     */
    public void setId_Corso(String id_Corso) { this.id_Corso = id_Corso; }

    /**
     * Gets nome corso.
     *
     * @return the nome corso
     */
    public String getNomeCorso() { return nomeCorso; }

    /**
     * Sets nome corso.
     *
     * @param nomeCorso the nome corso
     */
    public void setNomeCorso(String nomeCorso) { this.nomeCorso = nomeCorso; }

    /**
     * Gets capienza.
     *
     * @return the capienza
     */
    public int getCapienza() { return capienza; }

    /**
     * Sets capienza.
     *
     * @param capienza the capienza
     */
    public void setCapienza(int capienza) { this.capienza = capienza; }

    /**
     * Gets istruttore gestore.
     *
     * @return the istruttore gestore
     */
    public Istruttore getIstruttoreGestore() { return istruttoreGestore; }

    /**
     * Sets istruttore gestore.
     *
     * @param istruttoreGestore the istruttore gestore
     */
    public void setIstruttoreGestore(Istruttore istruttoreGestore) { this.istruttoreGestore = istruttoreGestore; }

    /**
     * Gets partecipazioni.
     *
     * @return the partecipazioni
     */
    public List<Partecipa> getPartecipazioni() { return partecipazioni; }

    /**
     * Sets partecipazioni.
     *
     * @param partecipazioni the partecipazioni
     */
    public void setPartecipazioni(List<Partecipa> partecipazioni) { this.partecipazioni = partecipazioni; }

    @Override
    public String toString() {
        return "Corso" + "nomeCorso='" + nomeCorso ;
    }
}