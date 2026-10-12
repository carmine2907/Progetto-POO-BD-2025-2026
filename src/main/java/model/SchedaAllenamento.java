package model;

import java.util.ArrayList;

/**
 * The type Scheda allenamento.
 */
public class SchedaAllenamento {
    private String id_scheda;
    private String descrizione;

    private Istruttore creatore;
    private Iscritto proprietario;


    /**
     * Instantiates a new Scheda allenamento.
     *
     * @param id_Scheda    the id scheda
     * @param descrizione  the descrizione
     * @param creatore     the creatore
     * @param proprietario the proprietario
     */
    public SchedaAllenamento(String id_Scheda, String descrizione, Istruttore creatore, Iscritto proprietario) {
        this.id_scheda = id_Scheda;
        this.descrizione = descrizione;
        this.creatore = creatore;
        this.proprietario = proprietario;
    }

    /**
     * Gets id scheda.
     *
     * @return the id scheda
     */
    public String getId_Scheda() { return id_scheda; }

    /**
     * Sets id scheda.
     *
     * @param id_Scheda the id scheda
     */
    public void setId_Scheda(String id_Scheda) { this.id_scheda = id_Scheda; }

    /**
     * Gets descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() { return descrizione; }

    /**
     * Sets descrizione.
     *
     * @param descrizione the descrizione
     */
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    /**
     * Gets creatore.
     *
     * @return the creatore
     */
    public Istruttore getCreatore() { return creatore; }

    /**
     * Sets creatore.
     *
     * @param creatore the creatore
     */
    public void setCreatore(Istruttore creatore) { this.creatore = creatore; }

    /**
     * Gets proprietario.
     *
     * @return the proprietario
     */
    public Iscritto getProprietario() { return proprietario; }

    /**
     * Sets proprietario.
     *
     * @param proprietario the proprietario
     */
    public void setProprietario(Iscritto proprietario) { this.proprietario = proprietario; }
}