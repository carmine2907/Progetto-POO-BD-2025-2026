package controller.Exceptions;


/**
 * The type Utente non autenticato exception.
 */
// Segnalata se un utente prova a fare azioni protette senza essersi autenticato
public class UtenteNonAutenticatoException extends Exception {
    /**
     * Instantiates a new Utente non autenticato exception.
     *
     * @param messaggio the messaggio
     */
    public UtenteNonAutenticatoException(String messaggio) {
        super(messaggio);
    }
}