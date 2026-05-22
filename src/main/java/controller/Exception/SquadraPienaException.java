package controller.Exception;

// Segnalata se si prova ad  aggiungere un atleta a una squadra che ha raggiunto il limite max
public class SquadraPienaException extends Exception {
    public SquadraPienaException(String messaggio) {
        super(messaggio);
    }
}