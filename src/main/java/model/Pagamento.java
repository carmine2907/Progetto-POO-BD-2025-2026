package model;

import java.util.Date;

public class Pagamento{
    private int IdPagamento;
    private double Importo;
    private Date dataPagamento;
    private StatoPag Stato;
    boolean StatoVal=false;
    public enum StatoPag{
        Approvato,
        Rifiutato,
        in_Attesa
    }
    public boolean Isvalido(boolean StatoVal){

        if (StatoVal==true){
            System.out.println("Stato pagamento Accettato");
            return true;
        }
         System.out.println("il pagamento è stato rifiutato o in attesa");
        return false;
    }

    public double Getimporto() { return Importo; }
    public void Setimporto(double Importo){this.Importo=Importo;
    }
}
