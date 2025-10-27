package ojoaldato.exception;

/**
 * Excepcion personalizada que se lanza cuando un elemento no se encuentra en
 * las estructuras de datos del modelo.
 */
public class ElementoNoEncontradoException extends BaseException {
    /**
     * Crea una nueva excepción que indica que un elemento no ha sido encontrado.
     *
     * @param message Descripción del error o información adicional.
     */
    public ElementoNoEncontradoException(String message) {
        super(message);
    }
}
