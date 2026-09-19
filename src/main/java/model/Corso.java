package model;

import java.util.List;

public class Corso {
    private String id_Corso;
    private String nomeCorso;
    private int capienza;

    private Istruttore istruttoreGestore;
    private List<Partecipa> partecipazioni;



    public Corso(String id_Corso, String nomeCorso, int capienza, Istruttore istruttoreGestore, List<Partecipa> partecipazioni) {
        this.id_Corso = id_Corso;
        this.nomeCorso = nomeCorso;
        this.capienza = capienza;
        this.istruttoreGestore = istruttoreGestore;
        this.partecipazioni = partecipazioni;
    }

    public String getId_Corso() { return id_Corso; }
    public void setId_Corso(String id_Corso) { this.id_Corso = id_Corso; }

    public String getNomeCorso() { return nomeCorso; }
    public void setNomeCorso(String nomeCorso) { this.nomeCorso = nomeCorso; }

    public int getCapienza() { return capienza; }
    public void setCapienza(int capienza) { this.capienza = capienza; }

    public Istruttore getIstruttoreGestore() { return istruttoreGestore; }
    public void setIstruttoreGestore(Istruttore istruttoreGestore) { this.istruttoreGestore = istruttoreGestore; }

    public List<Partecipa> getPartecipazioni() { return partecipazioni; }
    public void setPartecipazioni(List<Partecipa> partecipazioni) { this.partecipazioni = partecipazioni; }
}