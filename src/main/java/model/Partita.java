package model;

import java.util.Scanner;
import java.sql.Time;
import java.util.Date;

public class Partita
{
    public Partita(Time orarioPart, int idPartita, Date dataPartita)
    {
        OrarioPart = orarioPart;
        this.idPartita = idPartita;
        DataPartita = dataPartita;
    }

    public Time getOrarioPart() {return OrarioPart;}
    public void setOrarioPart(Time orarioPart) {OrarioPart = orarioPart;}

    public int getIdPartita() {return idPartita;}
    public void setIdPartita(int idPartita) {this.idPartita = idPartita;}

    public Date getDataPartita() {return DataPartita;}
    public void setDataPartita(Date dataPartita) {DataPartita = dataPartita;}

    public void RegistraRisultato()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci goal squadra 1");
        int risultatoSquadra1 = scanner.nextInt();
        System.out.println("Inserisci goal squadra 2");
        int risultatoSquadra2 = scanner.nextInt();

        System.out.println("Il risultato e:" + risultatoSquadra1 + "-" + risultatoSquadra2);
    }
    public boolean isDisputata()
    {
        if (vieneDisputata==true)
        {
            System.out.println("La partita si gioca");
            return true;
        }
        System.out.println("La partita non si gioca");
        return false;
    }

    Time OrarioPart;
    int idPartita;
    Date DataPartita;
    boolean vieneDisputata=false;
}
