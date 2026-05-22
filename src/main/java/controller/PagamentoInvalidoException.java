package controller.exception;


// Segnalata se un atleta tenta di iscriversi o partecipare senza pagamenti in regola
public class PagamentoInvalidoException extends Exception {
    public PagamentoInvalidoException(String messaggio) {
        super(messaggio);
    }
}
