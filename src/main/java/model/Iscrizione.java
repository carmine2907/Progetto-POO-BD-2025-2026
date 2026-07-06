package model;

import java.util.Date;

/**
 * The type Iscrizione.
 */
public class Iscrizione {
    private int idIscrizione;
    private Date dataIscrizione;
    private String stagione;
    private boolean valida;

    /**
     * Instantiates a new Iscrizione.
     *
     * @param idIscrizione   the id iscrizione
     * @param dataIscrizione the data iscrizione
     * @param stagione       the stagione
     * @param valida         the valida
     */
    public Iscrizione(int idIscrizione, Date dataIscrizione, String stagione, boolean valida) {
        this.idIscrizione = idIscrizione;
        this.dataIscrizione = dataIscrizione;
        this.stagione = stagione;
        this.valida = valida; // Default false se non specificato
    }

    /**
     * Gets id iscrizione.
     *
     * @return the id iscrizione
     */
    public int getIdIscrizione() { return idIscrizione; }

    /**
     * Sets id iscrizione.
     *
     * @param idIscrizione the id iscrizione
     */
    public void setIdIscrizione(int idIscrizione) {
        this.idIscrizione = idIscrizione;
    }

    /**
     * Gets data iscrizione.
     *
     * @return the data iscrizione
     */
    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    /**
     * Sets data iscrizione.
     *
     * @param dataIscrizione the data iscrizione
     */
    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    /**
     * Gets stagione.
     *
     * @return the stagione
     */
    public String getStagione() {
        return stagione;
    }

    /**
     * Sets stagione.
     *
     * @param stagione the stagione
     */
    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    /**
     * Is valida boolean.
     *
     * @return the boolean
     */
    public boolean isValida() { return valida; }

    /**
     * Sets valida.
     *
     * @param valida the valida
     */
    public void setValida(boolean valida) {
        this.valida = valida;
    }
}