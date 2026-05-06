import java.util.Date;

public class Atleta extends Utente
{
    private Date dataNascita;
    private String ruolo;
    private boolean pagamentoInReg = false;

    public Atleta (String login, String password, String nome, String cognome)
    {
        super(login, password, nome, cognome);
        this.dataNascita = dataNascita;
        this.ruolo = ruolo;
    }

    public Date getDataNascita() { return dataNascita; }
    public void setDataNascita(Date dataNascita) { this.dataNascita = dataNascita; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public boolean pagamentoInRegola(boolean pagamentoInReg)
    {
        if(pagamentoInReg == true)
        {
            System.out.println("Il pagamento è in regola");
        }
        return false;
    }

    public String toString() { return super.toString() +dataNascita; }
}
