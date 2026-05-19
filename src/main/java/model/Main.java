package model;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Atleta a1 = new Atleta("mariosdi","marsdi10","mario","sdino");

        Squadra squadra = new Squadra(1, "Juventus U19", "Under 19", 25);

        squadra.addAtleta(a1);

        System.out.println("Numero giocatori: " + squadra.getNumeroGiocatori());
    }
}