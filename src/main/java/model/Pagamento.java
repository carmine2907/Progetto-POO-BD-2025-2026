package model;

public class Pagamento {
    private String id_Pagamento;
    private double importo;

    private Iscritto iscritto;



    public Pagamento(String id_Pagamento, double importo, Iscritto iscritto) {
        this.id_Pagamento = id_Pagamento;
        this.importo = importo;
        this.iscritto = iscritto;
    }

    public String getId_Pagamento() { return id_Pagamento; }
    public void setId_Pagamento(String id_Pagamento) { this.id_Pagamento = id_Pagamento; }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public Iscritto getIscritto() { return iscritto; }
    public void setIscritto(Iscritto iscritto) { this.iscritto = iscritto; }
}