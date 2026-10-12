package model;

/**
 * The type Servizio wellness.
 */
public class ServizioWellness {
    private String id_ServizioWellness;
    private String nomeServizio;
    private Boolean disponibile;

    private MembroVip membroPrenotante;


    /**
     * Instantiates a new Servizio wellness.
     *
     * @param id_ServizioWellness the id servizio wellness
     * @param nomeServizio        the nome servizio
     * @param disponibile         the disponibile
     * @param membroPrenotante    the membro prenotante
     */
    public ServizioWellness(String id_ServizioWellness, String nomeServizio, Boolean disponibile, MembroVip membroPrenotante) {
        this.id_ServizioWellness = id_ServizioWellness;
        this.nomeServizio = nomeServizio;
        this.disponibile = disponibile;
        this.membroPrenotante = membroPrenotante;
    }

    /**
     * Gets id servizio wellness.
     *
     * @return the id servizio wellness
     */
    public String getId_ServizioWellness() { return id_ServizioWellness; }

    /**
     * Sets id servizio wellness.
     *
     * @param id_ServizioWellness the id servizio wellness
     */
    public void setId_ServizioWellness(String id_ServizioWellness) { this.id_ServizioWellness = id_ServizioWellness; }

    /**
     * Gets nome servizio.
     *
     * @return the nome servizio
     */
    public String getNomeServizio() { return nomeServizio; }

    /**
     * Sets nome servizio.
     *
     * @param nomeServizio the nome servizio
     */
    public void setNomeServizio(String nomeServizio) { this.nomeServizio = nomeServizio; }

    /**
     * Gets disponibile.
     *
     * @return the disponibile
     */
    public Boolean getDisponibile() { return disponibile; }

    /**
     * Sets disponibile.
     *
     * @param disponibile the disponibile
     */
    public void setDisponibile(Boolean disponibile) { this.disponibile = disponibile; }

    /**
     * Gets membro prenotante.
     *
     * @return the membro prenotante
     */
    public MembroVip getMembroPrenotante() { return membroPrenotante; }

    /**
     * Sets membro prenotante.
     *
     * @param membroPrenotante the membro prenotante
     */
    public void setMembroPrenotante(MembroVip membroPrenotante) { this.membroPrenotante = membroPrenotante; }
}