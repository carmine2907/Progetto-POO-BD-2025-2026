import model.Atleta;
import model.Squadra;
import database.ConnessioneDatabase;
import java.sql.Connection;
import java.util.Date;


public class Main {

    public static void main(String[] args) {

        Atleta a1 = new Atleta("mariosdi","mar206","mario","sdino","4 luglio 2006","difensore");

        Squadra squadra = new Squadra(1, "Juventus U19", "Under 19", 25);

        squadra.addAtleta(a1);

        System.out.println("Numero giocatori: " + squadra.getNumeroGiocatori());
    }
}