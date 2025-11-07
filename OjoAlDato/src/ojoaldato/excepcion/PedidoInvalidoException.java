package ojoaldato.excepcion;

public class PedidoInvalidoException extends BaseException {
    public PedidoInvalidoException(String message) {
        super(message);
    }

    public PedidoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
