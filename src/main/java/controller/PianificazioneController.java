package controller;

import controller.Exception.UtenteNonAutenticatoException;
import model.Campo;
import model.Partita;
import java.time.LocalDate;
import java.time.LocalTime;

public class PianificazioneController {

    public PianificazioneController() {}


    public Partita pianificaPartita(int idPartita, LocalDate data, LocalTime ora, Campo campo)
            throws UtenteNonAutenticatoException, IllegalStateException {

        if (!AutenticazioneController.isUtenteAutenticato()) {
            throw new UtenteNonAutenticatoException("Operazione consentita solo al personale autorizzato.");
        }

        // Controllo della regola di business sul campo
        if (!campo.isDisponibile()) {
            throw new IllegalStateException("Il campo '" + campo.getNome() + "' non è disponibile per la data e l'orario selezionati.");
        }

        // Creazione dell'entità partita
        Partita nuovaPartita = new Partita(idPartita, data, ora, campo);

        // Il campo adesso viene contrassegnato come occupato
        campo.setDisponibile(false);

        // NOTA: Qui inseriremo il salvataggio su DB tramite partitaDAO e campoDAO

        return nuovaPartita;
    }
}