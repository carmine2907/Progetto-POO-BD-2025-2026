package model;

public class Dirigente extends Utente
{
    private String ruoloOrganizzativo;

    public Dirigente (String login, String password, String nome, String cognome)
    {
        super(login, password, nome, cognome);
        this.ruoloOrganizzativo = ruoloOrganizzativo;
    }

    public String getRuoloOrganizzativo() { return ruoloOrganizzativo; }
    public void setRuoloOrganizzativo(String ruoloOrganizzativo)
    { this.ruoloOrganizzativo = ruoloOrganizzativo; }

    public String toString() { return super.toString(); }
}
