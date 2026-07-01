package model;

import java.util.Date;

public class Pagamento {

    public enum StatoPag {
        APPROVATO,
        RIFIUTATO,
        IN_ATTESA
    }

    private int idPagamento;
    private double importo;
    private Date dataPagamento;
    private StatoPag stato;

    public Pagamento(int idPagamento, double importo, Date dataPagamento, StatoPag stato) {
        this.idPagamento = idPagamento;
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.stato = stato;
    }

    public int getIdPagamento() { return idPagamento; }

    public void setIdPagamento(int idPagamento) { this.idPagamento = idPagamento; }

    public double getImporto() { return importo; }

    public void setImporto(double importo) { this.importo = importo; }

    public Date getDataPagamento() { return dataPagamento; }

    public void setDataPagamento(Date dataPagamento) { this.dataPagamento = dataPagamento; }

    public StatoPag getStato() { return stato; }

    public void setStato(StatoPag stato) { this.stato = stato; }

    // Il pagamento è valido solo se è esplicitamente approvato
    public boolean isValido() { return this.stato == StatoPag.APPROVATO; }

    @Override
    public String toString() {
        return "Pagamento {" + " " + stato + " "+'}';
    }
}