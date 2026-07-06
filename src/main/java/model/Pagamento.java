package model;

import java.util.Date;

/**
 * The type Pagamento.
 */
public class Pagamento {

    /**
     * The enum Stato pag.
     */
    public enum StatoPag {
        /**
         * Approvato stato pag.
         */
        APPROVATO,
        /**
         * Rifiutato stato pag.
         */
        RIFIUTATO,
        /**
         * In attesa stato pag.
         */
        IN_ATTESA
    }

    private int idPagamento;
    private double importo;
    private Date dataPagamento;
    private StatoPag stato;

    /**
     * Instantiates a new Pagamento.
     *
     * @param idPagamento   the id pagamento
     * @param importo       the importo
     * @param dataPagamento the data pagamento
     * @param stato         the stato
     */
    public Pagamento(int idPagamento, double importo, Date dataPagamento, StatoPag stato) {
        this.idPagamento = idPagamento;
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.stato = stato;
    }

    /**
     * Gets id pagamento.
     *
     * @return the id pagamento
     */
    public int getIdPagamento() { return idPagamento; }

    /**
     * Sets id pagamento.
     *
     * @param idPagamento the id pagamento
     */
    public void setIdPagamento(int idPagamento) { this.idPagamento = idPagamento; }

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
     * Gets data pagamento.
     *
     * @return the data pagamento
     */
    public Date getDataPagamento() { return dataPagamento; }

    /**
     * Sets data pagamento.
     *
     * @param dataPagamento the data pagamento
     */
    public void setDataPagamento(Date dataPagamento) { this.dataPagamento = dataPagamento; }

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public StatoPag getStato() { return stato; }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(StatoPag stato) { this.stato = stato; }

    /**
     * Is valido boolean.
     *
     * @return the boolean
     */
// Il pagamento è valido solo se è esplicitamente approvato
    public boolean isValido() { return this.stato == StatoPag.APPROVATO; }

    @Override
    public String toString() {
        return "Pagamento {" + " " + stato + " "+'}';
    }
}