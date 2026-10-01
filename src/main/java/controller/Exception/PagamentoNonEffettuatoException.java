package controller.Exception;

public class PagamentoNonEffettuatoException extends RuntimeException {
    public PagamentoNonEffettuatoException(String message) {
        super(message);
    }
}
