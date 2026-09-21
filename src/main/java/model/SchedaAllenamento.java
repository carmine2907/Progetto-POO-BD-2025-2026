package model;

public class SchedaAllenamento {
    private String id_scheda;
    private String descrizione;

    private Istruttore creatore;
    private Iscritto proprietario;



    public SchedaAllenamento(String id_Scheda, String descrizione, Istruttore creatore, Iscritto proprietario) {
        this.id_scheda = id_Scheda;
        this.descrizione = descrizione;
        this.creatore = creatore;
        this.proprietario = proprietario;
    }

    public String getId_Scheda() { return id_scheda; }
    public void setId_Scheda(String id_Scheda) { this.id_scheda = id_Scheda; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Istruttore getCreatore() { return creatore; }
    public void setCreatore(Istruttore creatore) { this.creatore = creatore; }

    public Iscritto getProprietario() { return proprietario; }
    public void setProprietario(Iscritto proprietario) { this.proprietario = proprietario; }
}