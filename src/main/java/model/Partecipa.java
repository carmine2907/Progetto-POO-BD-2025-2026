package model;

public class Partecipa  {
    private String id_Corso;
    private String id_utente;

    private Corso corso;
    private Iscritto iscritto;



    public Partecipa(String id_Corso, String id_Iscritto, Corso corso, Iscritto iscritto) {
        this.id_Corso = id_Corso;
        this.id_utente = id_utente;
        this.corso = corso;
        this.iscritto = iscritto;
    }

    public String getId_Corso() { return id_Corso; }
    public void setId_Corso(String id_Corso) { this.id_Corso = id_Corso; }

    public String getId_Iscritto() { return id_utente; }
    public void setId_Iscritto(String id_Iscritto) { this.id_utente = id_Iscritto; }

    public Corso getCorso() { return corso; }
    public void setCorso(Corso corso) { this.corso = corso; }

    public Iscritto getIscritto() { return iscritto; }
    public void setIscritto(Iscritto iscritto) { this.iscritto = iscritto; }
}