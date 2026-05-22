package controller.Exception;


// Segnalata se un utente prova a fare azioni protette senza essersi autenticato
public class UtenteNonAutenticatoException extends Exception {
    public UtenteNonAutenticatoException(String messaggio) {
        super(messaggio);
    }
}