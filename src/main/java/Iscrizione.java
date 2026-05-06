import java.util.Date;

public class Iscrizione
{
    private int Idiscrizione;
    private Date dataiscrizione;
    private String Stagione;
    boolean Validita = false;

    public boolean isValida(boolean Validita)
    {
        if (Validita==true)
        {
            System.out.println("l'iscrizione e valida");
        }
        System.out.println("l'iscrizione non e valida");
        return false;
    }

    public String getStagione() { return Stagione; }
    public void setStagione(String Stagione){this.Stagione= Stagione;
    }
}
