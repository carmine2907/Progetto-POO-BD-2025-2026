package model;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Partita.
 */
public class Partita {
    private int idPartita;
    private LocalDate dataPart;
    private LocalTime oraPart;

    private Campo campo;

    /**
     * Instantiates a new Partita.
     *
     * @param idPartita the id partita
     * @param dataPart  the data part
     * @param oraPart   the ora part
     * @param campo     the campo
     */
    public Partita(int idPartita, LocalDate dataPart, LocalTime oraPart, Campo campo) {
        this.idPartita = idPartita;
        this.dataPart = dataPart;
        this.oraPart = oraPart;
        this.campo = campo;
    }

    /**
     * Registra risultato.
     *
     * @param risultato the risultato
     */
    public void registraRisultato(String risultato) {
        System.out.println("Risultato: " + risultato);
    }

    /**
     * Is disputata boolean.
     *
     * @return the boolean
     */
    public boolean isDisputata() { return dataPart.isBefore(LocalDate.now()); }

    /**
     * Gets id partita.
     *
     * @return the id partita
     */
    public int getIdPartita() { return idPartita; }

    /**
     * Sets id partita.
     *
     * @param idPartita the id partita
     */
    public void setIdPartita(int idPartita) { this.idPartita = idPartita; }

    /**
     * Gets data part.
     *
     * @return the data part
     */
    public LocalDate getDataPart() { return dataPart;}

    /**
     * Sets data part.
     *
     * @param dataPart the data part
     */
    public void setDataPart(LocalDate dataPart) { this.dataPart = dataPart; }

    /**
     * Gets ora part.
     *
     * @return the ora part
     */
    public LocalTime getOraPart() { return oraPart; }

    /**
     * Sets ora part.
     *
     * @param oraPart the ora part
     */
    public void setOraPart(LocalTime oraPart) { this.oraPart = oraPart; }

    /**
     * Gets campo.
     *
     * @return the campo
     */
    public Campo getCampo() { return campo; }

    /**
     * Sets campo.
     *
     * @param campo the campo
     */
    public void setCampo(Campo campo) { this.campo = campo; }

    @Override
    public String toString() {

        return dataPart.toString() + " - " + oraPart.toString() + " | " + campo.getNome();
    }
}