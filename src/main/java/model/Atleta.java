package model;
import java.util.Date;

public class Atleta extends Utente
{
    private Date dataNascita;
    private String ruolo;
    private boolean pagamentoInReg;

    public Atleta (String login, String password, String nome, String cognome, Date dataNascita, String ruolo)
    {
        super(login, password, nome, cognome);
        this.dataNascita = dataNascita;
        this.ruolo = ruolo;
        this.pagamentoInReg = false;
    }

    public Date getDataNascita() { return dataNascita; }
    public void setDataNascita(Date dataNascita) { this.dataNascita = dataNascita; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public boolean isPagamentoInRegola() { return pagamentoInReg; }
    public void setPagamentoInRegola(boolean stato) { this.pagamentoInReg = stato; }

    public String toString() { return super.toString() +dataNascita; }
}
