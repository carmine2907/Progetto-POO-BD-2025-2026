package model;


import java.time.LocalDate;
import java.time.LocalTime;

public class Partita {
    private int idPartita;
    private LocalDate dataPart;
    private LocalTime oraPart;

    private Campo campo;

    public Partita(int idPartita, LocalDate dataPart, LocalTime oraPart, Campo campo) {
        this.idPartita = idPartita;
        this.dataPart = dataPart;
        this.oraPart = oraPart;
        this.campo = campo;
    }

    public void registraRisultato(String risultato) {
        System.out.println("Risultato: " + risultato);
    }

    public boolean isDisputata() { return dataPart.isBefore(LocalDate.now()); }

    public int getIdPartita() { return idPartita; }

    public void setIdPartita(int idPartita) { this.idPartita = idPartita; }

    public LocalDate getDataPart() { return dataPart;}

    public void setDataPart(LocalDate dataPart) { this.dataPart = dataPart; }

    public LocalTime getOraPart() { return oraPart; }

    public void setOraPart(LocalTime oraPart) { this.oraPart = oraPart; }

    public Campo getCampo() { return campo; }

    public void setCampo(Campo campo) { this.campo = campo; }
}