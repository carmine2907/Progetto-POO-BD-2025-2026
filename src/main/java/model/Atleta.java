package model;
import java.util.Date;

/**
 * The type Atleta.
 */
public class Atleta extends Utente
{
    private String dataNascita;
    private String ruolo;
    private boolean pagamentoInReg;

    /**
     * Instantiates a new Atleta.
     *
     * @param login       the login
     * @param password    the password
     * @param nome        the nome
     * @param cognome     the cognome
     * @param dataNascita the data nascita
     * @param ruolo       the ruolo
     */
    public Atleta (String login, String password, String nome, String cognome, String dataNascita, String ruolo)
    {
        super(login, password, nome, cognome);
        this.dataNascita = dataNascita;
        this.ruolo = ruolo;
        this.pagamentoInReg = isPagamentoInRegola();
    }

    /**
     * Gets data nascita.
     *
     * @return the data nascita
     */
    public String getDataNascita() { return dataNascita; }

    /**
     * Sets data nascita.
     *
     * @param dataNascita the data nascita
     */
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * Gets ruolo.
     *
     * @return the ruolo
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Sets ruolo.
     *
     * @param ruolo the ruolo
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Is pagamento in regola boolean.
     *
     * @return the boolean
     */
    public boolean isPagamentoInRegola() {
        return pagamentoInReg;
    }

    /**
     * Sets pagamento in regola.
     *
     * @param stato the stato
     */
    public void setPagamentoInRegola(boolean stato) {
        this.pagamentoInReg = stato;
    }

    @Override
    public String toString() {
        // Esempio: "Rossi Mario - Attaccante (10/05/2010)"
        return isPagamentoInRegola()+getCognome() + " " + getNome() + " - " + ruolo + " (" + dataNascita + ")";
    }
}
