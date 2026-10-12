package model;

/**
 * The type Pagamento.
 */
public class Pagamento {
    private String id_Pagamento;
    private double importo;

    private Iscritto iscritto;


    /**
     * Instantiates a new Pagamento.
     *
     * @param id_Pagamento the id pagamento
     * @param importo      the importo
     * @param iscritto     the iscritto
     */
    public Pagamento(String id_Pagamento, double importo, Iscritto iscritto) {
        this.id_Pagamento = id_Pagamento;
        this.importo = importo;
        this.iscritto = iscritto;
    }

    /**
     * Gets id pagamento.
     *
     * @return the id pagamento
     */
    public String getId_Pagamento() { return id_Pagamento; }

    /**
     * Sets id pagamento.
     *
     * @param id_Pagamento the id pagamento
     */
    public void setId_Pagamento(String id_Pagamento) { this.id_Pagamento = id_Pagamento; }

    /**
     * Gets importo.
     *
     * @return the importo
     */
    public double getImporto() { return importo; }

    /**
     * Sets importo.
     *
     * @param importo the importo
     */
    public void setImporto(double importo) { this.importo = importo; }

    /**
     * Gets iscritto.
     *
     * @return the iscritto
     */
    public Iscritto getIscritto() { return iscritto; }

    /**
     * Sets iscritto.
     *
     * @param iscritto the iscritto
     */
    public void setIscritto(Iscritto iscritto) { this.iscritto = iscritto; }
}