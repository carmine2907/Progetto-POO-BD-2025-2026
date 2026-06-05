package model;

import java.util.Date;

public class Iscrizione {
    private int idIscrizione;
    private Date dataIscrizione;
    private String stagione;
    private boolean valida;

    public Iscrizione(int idIscrizione, Date dataIscrizione, String stagione, boolean valida) {
        this.idIscrizione = idIscrizione;
        this.dataIscrizione = dataIscrizione;
        this.stagione = stagione;
        this.valida = valida; // Default false se non specificato
    }

    public int getIdIscrizione() { return idIscrizione; }

    public void setIdIscrizione(int idIscrizione) {
        this.idIscrizione = idIscrizione;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public boolean isValida() { return valida; }

    public void setValida(boolean valida) {
        this.valida = valida;
    }
}