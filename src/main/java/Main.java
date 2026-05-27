import model.Atleta;
import model.Squadra;

import java.util.Date;


public class Main {

    public static void main(String[] args) {
        Date dataNascita = new Date(2006 , 8, 4);
        Atleta a1 = new Atleta("mariosdi","mar206","mario","sdino",dataNascita,"difensore");

        Squadra squadra = new Squadra(1, "Juventus U19", "Under 19", 25);

        squadra.addAtleta(a1);

        System.out.println("Numero giocatori: " + squadra.getNumeroGiocatori());
    }
}