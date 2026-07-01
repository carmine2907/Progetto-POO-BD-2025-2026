package model;
import java.util.Date;

public class Atleta extends Utente
{
    private String dataNascita;
    private String ruolo;
    private boolean pagamentoInReg;

    public Atleta (String login, String password, String nome, String cognome, String dataNascita, String ruolo)
    {
        super(login, password, nome, cognome);
        this.dataNascita = dataNascita;
        this.ruolo = ruolo;
        this.pagamentoInReg = false;
    }

    public String getDataNascita() { return dataNascita; }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public boolean isPagamentoInRegola() {
        return pagamentoInReg;
    }

    public void setPagamentoInRegola(boolean stato) {
        this.pagamentoInReg = stato;
    }

    @Override
    public String toString() {
        // Esempio: "Rossi Mario - Attaccante (10/05/2010)"
        return getCognome() + " " + getNome() + " - " + ruolo + " (" + dataNascita + ")";
    }
}
